package com.yuguanzhang.lumi.user.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ViewController {

    @GetMapping("/main")
    public String main(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            model.addAttribute("email", email);
        }
        return "main";
    }
}
