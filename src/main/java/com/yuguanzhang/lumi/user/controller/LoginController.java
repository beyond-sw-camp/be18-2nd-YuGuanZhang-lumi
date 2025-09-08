package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.user.dto.LoginRequestDto;
import com.yuguanzhang.lumi.user.dto.LoginResponseDto;
import com.yuguanzhang.lumi.user.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = loginService.login(request);
        return ResponseEntity.ok(response);
    }
}
