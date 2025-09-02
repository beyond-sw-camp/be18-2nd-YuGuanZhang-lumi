package com.yuguanzhang.lumi.common.jwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final String secretKey;
    private final long expirationMs;

    public JwtServiceImpl(@Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expirationMs) {
        this.secretKey = secretKey;
        this.expirationMs = expirationMs;
    }


    @Override
    public String generateToken(String username) {
        return Jwts.builder().setSubject(username) // 토큰에 "누구인지" 저장
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()).compact();
    }

    @Override
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()) // 같은 키로 검증
                .parseClaimsJws(token) // 토큰 해석
                .getBody().getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
