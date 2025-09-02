package com.yuguanzhang.lumi.common.jwt.service;

public interface JwtService {
    // JWT 토큰을 생성 (사용자의 username 같은 식별자를 바탕으로)
    String generateToken(String username);

    // 토큰에서 username 추출
    String getUsername(String token);

    // 토큰 유효성 검증 (만료 여부, 서명 검증 등)
    boolean validateToken(String token);
}
