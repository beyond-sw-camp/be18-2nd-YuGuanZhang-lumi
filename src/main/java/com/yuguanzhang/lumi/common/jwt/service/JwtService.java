package com.yuguanzhang.lumi.common.jwt.service;

public interface JwtService {
    String generateToken(String username);

    String getUsername(String token);

    boolean validateToken(String token);
}
