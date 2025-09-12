package com.yuguanzhang.lumi.email.controller;

import com.yuguanzhang.lumi.common.exception.dto.BaseResponseDto;
import com.yuguanzhang.lumi.email.dto.EmailVerificationDto;
import com.yuguanzhang.lumi.email.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/api/email/send")
    public ResponseEntity<BaseResponseDto<Void>> sendEmailVerification(
            @RequestBody EmailVerificationDto emailVerificationDto) {
        // 비즈니스 로직은 서비스 계층으로 분리
        emailVerificationService.sendVerificationEmail(emailVerificationDto.getEmail());
        // 성공 응답만 반환
        return ResponseEntity.ok(BaseResponseDto.success(null, "이메일 인증 메일이 발송되었습니다.", 200));
    }

    @GetMapping("/api/email/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        return emailVerificationService.verifyEmail(token);
    }
}
