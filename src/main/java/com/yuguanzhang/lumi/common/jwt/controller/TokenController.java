package com.yuguanzhang.lumi.common.jwt.controller;

import com.yuguanzhang.lumi.common.jwt.dto.LoginResponseDto;
import com.yuguanzhang.lumi.common.jwt.dto.RefreshRequestDto;
import com.yuguanzhang.lumi.common.jwt.refresh.RefreshTokenStore;
import com.yuguanzhang.lumi.common.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
public class TokenController {

    private final JwtService jwtService;
    private final RefreshTokenStore refreshTokenStore;

    // 리프레시 토큰으로 새로운 Access Token 발급
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@RequestBody RefreshRequestDto request) {
        String refreshToken = request.getRefreshToken();

        // 1) 서명/만료 검사
        if (!jwtService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(401).build();
        }

        // 2) Redis에 저장된 토큰과 동일한지 검증
        String email = jwtService.getUsername(refreshToken);
        String saved = refreshTokenStore.find(email);
        if (saved == null || !saved.equals(refreshToken)) {
            return ResponseEntity.status(401).build();
        }

        // 3) 새 Access 발급 (회전 안 함)
        String newAccess = jwtService.generateAccessToken(email);

        return ResponseEntity.ok(new LoginResponseDto(email, newAccess, refreshToken));
    }

    // 로그아웃: 리프레시 토큰 삭제
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequestDto request) {
        String refreshToken = request.getRefreshToken();
        if (jwtService.validateRefreshToken(refreshToken)) {
            String email = jwtService.getUsername(refreshToken);
            refreshTokenStore.delete(email);
        }
        return ResponseEntity.noContent().build();
    }
}
