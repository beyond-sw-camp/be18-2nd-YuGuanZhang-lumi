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
    // private final UserRepository userRepository; // ✅ UserRepository는 더 이상 필요 없습니다.

    @Override
    @Transactional
    public void sendVerificationEmail(String email) {
        // 기존 이메일 인증 기록이 있는지 확인
        Optional<EmailVerification> existingVerification =
                emailVerificationRepository.findByEmail(email);

        String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);

        EmailVerification verification;
        if (existingVerification.isPresent()) {
            log.info("기존 인증 기록을 업데이트합니다. 이메일: {}", email);
            verification = existingVerification.get();
            verification.updateForResend(token, expirationTime);
        } else {
            log.info("새로운 인증 기록을 생성합니다. 이메일: {}", email);
            verification = EmailVerification.builder().email(email) // ✅ User가 아닌 email 필드를 사용합니다.
                    .verification_code(token).status(VerificationStatus.UNREAD)
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
    public boolean verifyEmail(String token) {
        String redisKey = "email:verify:" + token;
        String email = redisTemplate.opsForValue().get(redisKey);

        if (email == null) {
            log.warn("유효하지 않거나 만료된 토큰입니다: {}", token);
            return false;
        }

        // ✅ DB에서 이메일로 인증 기록을 찾습니다.
        Optional<EmailVerification> optionalVerification =
                emailVerificationRepository.findByEmail(email);
        if (optionalVerification.isEmpty()) {
            log.error("인증 기록을 찾을 수 없습니다. 이메일: {}", email);
            return false;
        }

        EmailVerification verification = optionalVerification.get();
        if (LocalDateTime.now().isAfter(verification.getExpiration_at())) {
            log.warn("토큰이 만료되었습니다. 이메일: {}", email);
            verification.markAsExpired();
            return false;
        }
        if (!verification.getVerification_code().equals(token)) {
            log.warn("인증 코드가 일치하지 않습니다. 토큰: {}", token);
            return false;
        }

        log.info("이메일 인증 성공. 이메일: {}", email);
        verification.markAsVerified();

        // Redis에서 토큰 삭제
        redisTemplate.delete(redisKey);
        return true;
    }

    @Override
    public boolean isEmailVerified(String email) {
        // ✅ DB에서 이메일 인증 기록을 찾아 상태가 'VERIFIED'인지 확인합니다.
        return emailVerificationRepository.findByEmail(email).map(EmailVerification::getStatus)
                .filter(status -> status == VerificationStatus.VERIFIED).isPresent();
    }
}
