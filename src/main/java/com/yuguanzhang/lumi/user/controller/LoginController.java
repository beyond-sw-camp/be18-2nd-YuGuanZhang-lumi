package com.yuguanzhang.lumi.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // http://경:8080 으로도 들어갈 수 있게 변
    @GetMapping({"/", "/api/login"})
    public String login() {
        return "login";
    }
}

