package com.yuguanzhang.lumi.user.service;

import com.yuguanzhang.lumi.user.dto.SignupRequestDto;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
         회원가입 처리
     */
    public void processSignup(SignupRequestDto signupRequestDto) {
        User users = signupRequestDto.toUser(passwordEncoder);  // DTO → User 변환
        userRepository.save(users);  // DB 저장
    }

    // 이메일 기준으로 회원을 찾을 때 동작, 나중에 기능 만드면 따로 때는것도 고려해보면 좋을거 같음
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);

    }
}
