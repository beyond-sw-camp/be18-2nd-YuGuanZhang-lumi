package com.yuguanzhang.lumi.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


// @RestController 나중에 vue 쓸때 고쳐야함
@Controller
public class LoginController {

    // http://경:8080 으로도 들어갈 수 있게 변
    @GetMapping({"/", "login"})
    public String login() {
        return "login";
    }

}

