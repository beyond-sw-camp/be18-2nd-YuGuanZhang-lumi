package com.yuguanzhang.lumi.common.jwt.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final Key key;
    private final long accessExpMillis;
    private final long refreshExpMillis;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration}") long accessExpMillis,
            @Value("${jwt.refresh-expiration}") long refreshExpMillis) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessExpMillis = accessExpMillis;
        this.refreshExpMillis = refreshExpMillis;
    }

    @Override
    public String generateAccessToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpMillis))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpMillis))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    @Override
    public String getUsername(String token) {
        Claims claims =
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    @Override
    public boolean validateAccessToken(String token) {
        return validateToken(token);
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return validateToken(token);
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
