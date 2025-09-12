package com.yuguanzhang.lumi.user.service.signup;

import com.yuguanzhang.lumi.email.repository.EmailVerificationRepository;
import com.yuguanzhang.lumi.email.service.EmailVerificationService;
import com.yuguanzhang.lumi.user.dto.sigup.SignupRequestDto;
import com.yuguanzhang.lumi.user.dto.sigup.SignupResponseDto;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;
    private final EmailVerificationRepository emailVerificationRepository;

    @Override
    @Transactional
    public SignupResponseDto processSignup(SignupRequestDto signupRequestDto) {
        if (signupRequestDto.getIsPrivacyAgreement() == null || !signupRequestDto.getIsPrivacyAgreement()) {
            throw new IllegalArgumentException("개인정보 동의가 필요합니다.");
        }

        if (!emailVerificationService.isEmailVerified(signupRequestDto.getEmail())) {
            throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
        }

        if (userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = signupRequestDto.toUser(passwordEncoder);
        user.markAsVerified();
        User savedUser = userRepository.save(user);

        emailVerificationRepository.findByEmail(signupRequestDto.getEmail())
                .ifPresent(verification -> verification.setUser(savedUser));

        return new SignupResponseDto("회원가입 성공", savedUser.getEmail(), savedUser.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
