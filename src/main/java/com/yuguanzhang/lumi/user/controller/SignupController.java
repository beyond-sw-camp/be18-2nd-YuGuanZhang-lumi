package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.user.service.SignUpService;
import com.yuguanzhang.lumi.user.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
public class SignupController {

    private final SignUpService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.processSignup(signupRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }
}

