package com.yuguanzhang.lumi.user.service.login;

import com.yuguanzhang.lumi.user.dto.login.LoginRequestDto;
import com.yuguanzhang.lumi.user.dto.login.LoginResponseDto;
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
public class LoginServiceImpl implements LoginService {
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
        // AccessToken은 지금 저장 안하고 있음, 프론트 개발 시 어디에 저장할 지 정해야 (메모리/세션/쿠키)
        // RefreshToken은 redis에서도 저장해야하고 프런트에서도 저장하고 있어야한다고 함
        String accessToken = jwtService.generateAccessToken(userDetails.getUsername());
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

        // Redis에 Refresh 토큰 저장
        refreshTokenStore.save(userDetails.getUsername(), refreshToken, 604800000); // 7일

        // 생성한 JWT와 사용자 이름을 LoginResponseDto에 담아 반환
        return new LoginResponseDto(userDetails.getUsername(), accessToken, refreshToken);
    }
}
