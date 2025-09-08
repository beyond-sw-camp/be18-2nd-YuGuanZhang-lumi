package com.yuguanzhang.lumi.common.jwt.service.Refresh;

import com.yuguanzhang.lumi.user.dto.LoginResponseDto;
import com.yuguanzhang.lumi.common.jwt.dto.RefreshRequestDto;
import com.yuguanzhang.lumi.common.jwt.refresh.RefreshTokenStore;
import com.yuguanzhang.lumi.common.jwt.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService {

    private final JwtService jwtService;

    private final RefreshTokenStore refreshTokenStore;

    @Override
    public LoginResponseDto refresh(RefreshRequestDto request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 RefreshToken");
        }

        String email = jwtService.getUsername(refreshToken);
        String saved = refreshTokenStore.find(email);
        if (saved == null || !saved.equals(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰 불일치 또는 만료");
        }

        String newAccess = jwtService.generateAccessToken(email);

        return new LoginResponseDto(email, newAccess, refreshToken);
    }
}
