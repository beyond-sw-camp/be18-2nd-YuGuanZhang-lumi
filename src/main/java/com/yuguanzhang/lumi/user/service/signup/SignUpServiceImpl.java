package com.yuguanzhang.lumi.user.service.signup;

import com.yuguanzhang.lumi.user.dto.sigup.SignupRequestDto;
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

    @Override
    @Transactional
    public void processSignup(SignupRequestDto signupRequestDto) {
        // 개인정보 동의 체크
        if (signupRequestDto.getIsPrivacyAgreement() == null || !signupRequestDto.getIsPrivacyAgreement()) {
            throw new IllegalArgumentException("개인정보 동의가 필요합니다.");
        }

        if (userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // DTO → Entity 변환 후 저장
        User user = signupRequestDto.toUser(passwordEncoder);
        userRepository.save(user);
    }

    // 이메일 중복 체크 나중에 고도화 할 때..
    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
