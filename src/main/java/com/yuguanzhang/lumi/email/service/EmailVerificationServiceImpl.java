// File: com.yuguanzhang.lumi.email.service.EmailVerificationServiceImpl.java

package com.yuguanzhang.lumi.email.service;

import com.yuguanzhang.lumi.email.entity.EmailVerification;
import com.yuguanzhang.lumi.email.enums.VerificationStatus;
import com.yuguanzhang.lumi.email.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.internet.MimeMessage;

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
        // 기존 이메일 인증 기록이 있는지 확인
        Optional<EmailVerification> existingVerification =
                emailVerificationRepository.findByEmail(email);

        String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime now = LocalDateTime.now();

        EmailVerification verification;
        // email 컬럼이 unique로 걸려 있어서 같은 이메일로 여러 개 인증 레코드를 만들면 충돌
        // 그래서 updateForResend 이걸로 기존 인증 요청 상태를 바꿈
        if (existingVerification.isPresent()) {
            log.info("기존 인증 기록을 업데이트합니다. 이메일: {}", email);
            verification = existingVerification.get();
            verification.updateForResend(token, expirationTime);
            verification.setDateTime_at(now);
        } else {
            log.info("새로운 인증 기록을 생성합니다. 이메일: {}", email);
            // 회원가입 전에 이메일 인증 가능 구조
            verification = EmailVerification.builder().email(email).verification_code(token)
                    .status(VerificationStatus.UNREAD).dateTime_at(now)
                    .expiration_at(expirationTime).build();
        }

        try {
            // 이메일 인증 기록을 먼저 DB에 저장합니다.
            emailVerificationRepository.save(verification);

            String redisKey = "email:verify:" + token;
            redisTemplate.opsForValue().set(redisKey, email, 10, TimeUnit.MINUTES);

            String link = "http://localhost:8080/api/email/verify?token=" + token;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[lumi] 이메일 인증");
            helper.setText(
                    "<html><body>" + "<p>이메일 인증을 위해 아래 링크를 클릭해주세요:</p>" + "<a href=\"" + link + "\">" + link + "</a>" + "</body></html>",
                    true);

            mailSender.send(message);

            log.info("이메일 발송 성공. 이메일: {}", email);
        } catch (Exception e) {
            log.error("이메일 전송 중 오류가 발생했습니다: {}", e.getMessage(), e);
            verification.markAsError();
        }
    }

    @Override
    @Transactional
    public String verifyEmail(String token) {
        String redisKey = "email:verify:" + token;
        String email = redisTemplate.opsForValue().get(redisKey);

        if (email == null) {
            log.warn("유효하지 않거나 만료된 토큰입니다: {}", token);
            // 예외 발생: GlobalExceptionHandler가 처리
            throw new IllegalArgumentException("이메일 인증에 실패했거나 만료되었습니다.");
        }

        Optional<EmailVerification> optionalVerification =
                emailVerificationRepository.findByEmail(email);
        if (optionalVerification.isEmpty()) {
            log.error("인증 기록을 찾을 수 없습니다. 이메일: {}", email);
            throw new IllegalArgumentException("이메일 인증에 실패했습니다.");
        }

        EmailVerification verification = optionalVerification.get();
        if (LocalDateTime.now().isAfter(verification.getExpiration_at())) {
            log.warn("토큰이 만료되었습니다. 이메일: {}", email);
            verification.markAsExpired();
            throw new IllegalArgumentException("이메일 인증에 실패했거나 만료되었습니다.");
        }
        if (!verification.getVerification_code().equals(token)) {
            log.warn("인증 코드가 일치하지 않습니다. 토큰: {}", token);
            throw new IllegalArgumentException("이메일 인증에 실패했습니다.");
        }

        log.info("이메일 인증 성공. 이메일: {}", email);
        verification.markAsVerified();

        // Redis에서 토큰 삭제
        redisTemplate.delete(redisKey);
        
        return "<html><body><h3>이메일 인증이 완료되었습니다.</h3></body></html>";
    }

    @Override
    public boolean isEmailVerified(String email) {
        return emailVerificationRepository.findByEmail(email)
                .map(EmailVerification::getStatus) // Optional<EmailVerification> → Optional<VerificationStatus> 로 변환
                .filter(status -> status == VerificationStatus.VERIFIED).isPresent();
    }
}
