package com.yuguanzhang.lumi.common.jwt.service.logout;

import com.yuguanzhang.lumi.common.jwt.dto.RefreshRequestDto;
import com.yuguanzhang.lumi.common.jwt.refresh.RefreshTokenStore;
import com.yuguanzhang.lumi.common.jwt.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final JwtService jwtService;
    private final RefreshTokenStore refreshTokenStore;

    @Override
    public void logout(RefreshRequestDto request) {
        String refreshToken = request.getRefreshToken();
        if (jwtService.validateRefreshToken(refreshToken)) {
            String email = jwtService.getUsername(refreshToken);
            refreshTokenStore.delete(email);
        }
    }
}
