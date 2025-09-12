package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.common.exception.dto.BaseResponseDto;
import com.yuguanzhang.lumi.user.dto.sigup.SignupRequestDto;
import com.yuguanzhang.lumi.user.dto.sigup.SignupResponseDto;
import com.yuguanzhang.lumi.user.service.signup.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignUpService signUpService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<BaseResponseDto<SignupResponseDto>> signup(
            @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto responseDto = signUpService.processSignup(signupRequestDto);

        return ResponseEntity.ok(
                BaseResponseDto.success(responseDto, "회원가입 성공", HttpStatus.OK.value()));
    }
}
