package com.yuguanzhang.lumi.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ViewController {

    @GetMapping("/main")
    public String main(Model model, Principal principal) {
        // 로그인 되어 있으면 Principal에서 이메일 가져오기
        if (principal != null) {
            String email = principal.getName();
            model.addAttribute("email", email);
        } else {
            model.addAttribute("email", "Guest");
        }
        return "main";
    }
}
