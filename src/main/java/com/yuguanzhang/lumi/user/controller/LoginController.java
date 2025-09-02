package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.common.jwt.dto.LoginRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    // http://경:8080 으로도 들어갈 수 있게 변
    @GetMapping({"/", "login"})
    public String login() {
        return "login";
    }
    
}

