package com.yuguanzhang.lumi.email.controller;

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
    public ResponseEntity<String> sendEmailVerification(
            @RequestBody EmailVerificationDto emailVerificationDto) {
        emailVerificationService.sendVerificationEmail(emailVerificationDto.getEmail());
        return ResponseEntity.ok("이메일 인증 메일이 발송되었습니다.");
    }

    @GetMapping("/api/email/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        boolean verified = emailVerificationService.verifyEmail(token);
        if (verified) {
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("이메일 인증에 실패했거나 만료되었습니다.");
        }
    }
}
