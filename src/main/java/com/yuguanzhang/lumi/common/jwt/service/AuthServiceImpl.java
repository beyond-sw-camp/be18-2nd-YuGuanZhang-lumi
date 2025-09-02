package com.yuguanzhang.lumi.common.jwt.service;

import com.yuguanzhang.lumi.common.jwt.dto.LoginRequestDto;
import com.yuguanzhang.lumi.common.jwt.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        // 클라이언트가 보낸 이메일과 비밀번호로 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // 인증 성공하면 UserDetails 객체를 가져옴 (사용자 정보 포함)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 사용자 이름(username)으로 JWT 토큰 생성
        String token = jwtService.generateToken(userDetails.getUsername());

        // 생성한 JWT와 사용자 이름을 LoginResponseDto에 담아 반환
        return new LoginResponseDto(userDetails.getUsername(), token);
    }
}
