package com.yuguanzhang.lumi.email.service;

import com.yuguanzhang.lumi.email.entity.EmailVerification;
import com.yuguanzhang.lumi.email.enums.VerificationStatus;
import com.yuguanzhang.lumi.email.repository.EmailVerificationRepository;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void sendVerificationEmail(String email) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            log.error("User with email {} not found. Cannot send verification email.", email);
            return;
        }
        User user = existingUser.get();

        Optional<EmailVerification> existingVerification =
                emailVerificationRepository.findByUser(user);

        EmailVerification verification;
        if (existingVerification.isPresent()) {
            // 이미 존재하면 기존 객체를 가져와 메서드를 통해 상태를 업데이트합니다.
            log.info("기존 인증 기록을 업데이트합니다. 사용자: {}", user.getEmail());
            verification = existingVerification.get();
            verification.updateForResend(token, expirationTime);
        } else {
            // 존재하지 않으면 새로운 객체를 Builder를 통해 생성합니다.
            log.info("새로운 인증 기록을 생성합니다. 사용자: {}", user.getEmail());
            verification = EmailVerification.builder().user(user).verification_code(token)
                    .status(VerificationStatus.UNREAD).expiration_at(expirationTime).build();
        }

        // 이메일 전송 로직을 try-catch로 감싸서 오류를 처리합니다.
        try {
            // 이메일 인증 기록을 먼저 저장합니다.
            emailVerificationRepository.save(verification);

            String redisKey = "email:verify:" + token;
            redisTemplate.opsForValue().set(redisKey, email, 10, TimeUnit.MINUTES);

            String link = "http://localhost:8080/api/email/verify?token=" + token;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[lumi] 이메일 인증");

            String htmlContent =
                    "<html><body>" + "<p>이메일 인증을 위해 아래 링크를 클릭해주세요:</p>" + "<a href=\"" + link + "\">" + link + "</a>" + "</body></html>";
            helper.setText(htmlContent, true);

            mailSender.send(message);

            log.info("이메일 발송 성공. 사용자: {}", user.getEmail());
        } catch (Exception e) {
            log.error("이메일 전송 중 오류가 발생했습니다: {}", e.getMessage(), e);
            // 오류 발생 시 엔티티의 상태를 ERROR로 변경합니다.
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

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.error("인증 이메일과 연결된 사용자가 데이터베이스에 없습니다. 이메일: {}", email);
            return false;
        }
        User user = optionalUser.get();

        Optional<EmailVerification> optionalVerification =
                emailVerificationRepository.findByUser(user);

        if (optionalVerification.isPresent()) {
            EmailVerification verification = optionalVerification.get();

            // 1. 만료 시간을 확인합니다.
            if (LocalDateTime.now().isAfter(verification.getExpiration_at())) {
                log.warn("토큰이 만료되었습니다. 사용자: {}", user.getEmail());
                verification.markAsExpired();
                return false;
            }

            // 2. 토큰이 유효한지 확인합니다.
            if (!verification.getVerification_code().equals(token)) {
                log.warn("인증 코드가 일치하지 않습니다. 토큰: {}", token);
                return false;
            }

            // 인증 성공: User와 EmailVerification의 상태를 변경합니다.
            log.info("이메일 인증 성공. 사용자: {}", user.getEmail());
            user.markAsVerified();
            verification.markAsVerified();

            // Redis에서 토큰 삭제
            redisTemplate.delete(redisKey);
            return true;
        }

        log.warn("사용자({})에 대한 인증 기록을 찾을 수 없습니다.", user.getEmail());
        return false;
    }

    @Override
    public boolean isEmailVerified(String email) {
        return userRepository.findByEmail(email).map(User::getIsVerified).orElse(false);
    }
}
