package com.yuguanzhang.lumi.user.Service;

import com.yuguanzhang.lumi.user.dto.SignupRequestDto;
import com.yuguanzhang.lumi.user.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor   // 생성자 주입 (final 필드 권장)
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리
     */
    public void processSignup(SignupRequestDto signupRequestDto) {
        User users = signupRequestDto.toUser(passwordEncoder);  // DTO → User 변환
        userRepository.save(users);  // DB 저장
    }

    /*
     * 로그인 시 이메일(username)로 사용자 조회
     * 스프링 시큐리티에서 로그인 시 자동 호출되는 메서드

     * DB에서 email 기준으로 사용자 조회 (findByEmail)

     * 없으면 UsernameNotFoundException 발생

     * 있으면 UserDetails (즉, User 객체) 반환
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("username '" + username + "' not found"));
    }
}
