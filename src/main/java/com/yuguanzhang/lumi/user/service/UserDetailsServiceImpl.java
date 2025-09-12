package com.yuguanzhang.lumi.user.service;

import com.yuguanzhang.lumi.email.repository.EmailVerificationRepository;
import com.yuguanzhang.lumi.user.dto.UserDetailsDto;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
// implements UserDetailsService 시큐리티에서 제공하는 라이브러리
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // email 기준으로 유저 조회
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email));
        // UsernamePasswordAuthenticationToken = Spring Security에서 제공하는 클래스

        // 이메일 인증 여부 확인
        if (!user.getIsVerified()) {
            throw new UsernameNotFoundException("이메일이 확인되지 않았습니다.");
        }

        // User 엔티티 → UserDetailsDto 변환
        return new UserDetailsDto(user.getEmail(), user.getPassword(), user.getName());
    }
}
