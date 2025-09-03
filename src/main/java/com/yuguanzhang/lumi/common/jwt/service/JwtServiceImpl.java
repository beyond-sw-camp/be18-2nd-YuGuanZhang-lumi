package com.yuguanzhang.lumi.common.jwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final String secret;
    private final long accessExpMillis;
    private final long refreshExpMillis;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration}") long accessExpMillis,
            @Value("${jwt.refresh-expiration}") long refreshExpMillis) {
        this.secret = secret;
        this.accessExpMillis = accessExpMillis;
        this.refreshExpMillis = refreshExpMillis;
    }

    @Override
    public String generateAccessToken(String username) {
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + accessExpMillis))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpMillis))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
    }

    @Override
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody()
                .getSubject();
    }

    @Override
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
