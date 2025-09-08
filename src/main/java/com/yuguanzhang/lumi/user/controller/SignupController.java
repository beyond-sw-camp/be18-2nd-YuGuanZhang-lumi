package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.user.service.SignUpService;
import com.yuguanzhang.lumi.user.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignUpService userService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            userService.processSignup(signupRequestDto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}

