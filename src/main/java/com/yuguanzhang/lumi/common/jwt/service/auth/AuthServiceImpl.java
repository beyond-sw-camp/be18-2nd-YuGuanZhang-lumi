package com.yuguanzhang.lumi.common.jwt.service.auth;

import com.yuguanzhang.lumi.common.jwt.dto.LoginRequestDto;
import com.yuguanzhang.lumi.common.jwt.dto.LoginResponseDto;
import com.yuguanzhang.lumi.common.jwt.refresh.RefreshTokenStore;
import com.yuguanzhang.lumi.common.jwt.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final RefreshTokenStore refreshTokenStore;

    @Value("${jwt.refresh-expiration}")
    private long refreshTtlMillis;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        // 클라이언트가 보낸 이메일과 비밀번호로 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // 인증 성공하면 UserDetails 객체를 가져옴 (사용자 정보 포함)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // AccessToken, RefreshToken 발급
        String accessToken = jwtService.generateAccessToken(userDetails.getUsername());
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

        // Redis에 Refresh 토큰 저장
        refreshTokenStore.save(userDetails.getUsername(), refreshToken, 604800000); // 7일

        // 생성한 JWT와 사용자 이름을 LoginResponseDto에 담아 반환
        return new LoginResponseDto(userDetails.getUsername(), accessToken, refreshToken);
    }
}
