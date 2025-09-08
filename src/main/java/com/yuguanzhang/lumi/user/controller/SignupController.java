package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.user.dto.sigup.SignupResponseDto;
import com.yuguanzhang.lumi.user.service.signup.SignUpService;
import com.yuguanzhang.lumi.user.dto.sigup.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignUpService userService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<SignupResponseDto> signup(
            @RequestBody SignupRequestDto signupRequestDto) {
        try {
            userService.processSignup(signupRequestDto);
            SignupResponseDto responseDto =
                    new SignupResponseDto("회원가입 성공", signupRequestDto.getEmail(),
                            signupRequestDto.getName());
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException ex) {
            SignupResponseDto errorResponseDto =
                    new SignupResponseDto(ex.getMessage(), signupRequestDto.getEmail(),
                            signupRequestDto.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
        }
    }
}

