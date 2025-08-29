package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.user.service.SignUpService;
import com.yuguanzhang.lumi.user.dto.SignupRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class SignupController {

    private final SignUpService userService;

    /*
        브라우저 form에서 회원가입
     */
    @GetMapping("/api/sign_up")
    public String signup() {
        return "sign_up";
    }

    /*
        브라우저 form에서 POST
     */
    @PostMapping(value = "/api/sign_up", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String processSignupForm(SignupRequestDto signupRequestDto) {
        userService.processSignup(signupRequestDto);
        return "redirect:/api/login"; // 회원가입 후 로그인 페이지로
    }

}
