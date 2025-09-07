package com.yuguanzhang.lumi.email.service;

import com.yuguanzhang.lumi.email.entity.EmailVerification;
import com.yuguanzhang.lumi.email.repository.EmailVerificationRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JavaMailSender mailSender;
    private final EmailVerificationRepository emailVerificationRepository;

    @Override
    @Transactional
    public void sendVerificationEmail(String email) {
        String token = UUID.randomUUID().toString();
        // 10분 유효기간 설정
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);

        Optional<EmailVerification> existingVerification =
                emailVerificationRepository.findByEmail(email);

        // isPresent() 객체가 값을 가지고 있는지 여부를 확인하는 메서드
        if (existingVerification.isPresent()) {
            EmailVerification existing = existingVerification.get();
            EmailVerification updatedVerification =
                    EmailVerification.builder().verificationId(existing.getVerificationId())
                            .email(existing.getEmail()).verification_code(token).verified(false)
                            .expiration_at(expirationTime).build();
            emailVerificationRepository.save(updatedVerification);
        } else {
            EmailVerification verification =
                    EmailVerification.builder().email(email).verification_code(token)
                            .verified(false).expiration_at(expirationTime).build();
            emailVerificationRepository.save(verification);
        }

        String redisKey = "email:verify:" + token;
        redisTemplate.opsForValue().set(redisKey, email, 10, TimeUnit.MINUTES);

        String link = "http://localhost:8080/api/email/verify?token=" + token;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[lumi] 이메일 인증");

            String htmlContent =
                    "<html><body>" + "<p>이메일 인증을 위해 아래 링크를 클릭해주세요:</p>" + "<a href=\"" + link + "\">" + link + "</a>" + "</body></html>";
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("이메일 전송 중 오류가 발생했습니다: {}", e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public boolean verifyEmail(String token) {
        // Redis에서 토큰으로 이메일 정보 조회
        String redisKey = "email:verify:" + token;
        // opsForValue()는 가장 기본적인 키-값(key-value) 구조에 대한 연산자(operations)를 제공합니다.
        String email = redisTemplate.opsForValue().get(redisKey);

        if (email == null) {
            // Redis에 토큰이 없거나 만료됨
            return false;
        }

        Optional<EmailVerification> optionalVerification =
                emailVerificationRepository.findByEmail(email);

        if (optionalVerification.isPresent()) {
            EmailVerification verification = optionalVerification.get();

            // 만료 시간 체크
            if (LocalDateTime.now().isAfter(verification.getExpiration_at())) {
                return false;
            }

            // DB에 저장된 인증 코드와 Redis에서 가져온 토큰이 일치하는지 확인
            if (verification.getVerification_code().equals(token)) {
                // 인증 성공: 엔티티의 상태를 직접 변경하는 전용 메서드를 사용합니다.
                verification.markAsVerified();
                // @Transactional 덕분에 별도의 save() 호출 없이도 상태가 반영됩니다.

                // Redis 토큰 삭제
                redisTemplate.delete(redisKey);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmailVerified(String email) {
        return emailVerificationRepository.existsByEmailAndVerifiedIsTrue(email);
    }
}
