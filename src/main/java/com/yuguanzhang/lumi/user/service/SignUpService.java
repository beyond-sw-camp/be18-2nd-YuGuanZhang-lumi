package com.yuguanzhang.lumi.user.service;

import com.yuguanzhang.lumi.user.dto.SignupRequestDto;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor   // 생성자 주입 (final 필드 권장)
@Transactional
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

    // 이메일 기준으로 회원을 찾을 때 동작
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);

    }
}
