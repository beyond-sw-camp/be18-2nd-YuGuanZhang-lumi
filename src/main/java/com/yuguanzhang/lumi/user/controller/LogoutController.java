package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.common.exception.dto.BaseResponseDto;
import com.yuguanzhang.lumi.common.jwt.dto.RefreshRequestDto;
import com.yuguanzhang.lumi.user.dto.logout.LogoutResponseDto;
import com.yuguanzhang.lumi.user.service.logout.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogoutController {
    private final LogoutService logoutService;

    // 로그아웃: 리프레시 토큰 삭제
    @PostMapping("/api/logout")
    public ResponseEntity<BaseResponseDto<LogoutResponseDto>> logout(
            @RequestBody RefreshRequestDto request) {
        logoutService.logout(request);
        LogoutResponseDto response = logoutService.logout(request);
        return ResponseEntity.ok(BaseResponseDto.success(response, "로그아웃 성공", 200));
    }
}
