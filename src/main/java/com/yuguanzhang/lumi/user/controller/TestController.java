package com.yuguanzhang.lumi.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // 인증 필요 없는 API (GET/POST 모두 허용)
    @GetMapping("/api/public/hello")
    public String publicHelloGet() {
        return "Public Hello! GET 요청, 인증 필요 없음";
    }

    @PostMapping("/api/public/hello")
    public String publicHelloPost() {
        return "Public Hello! POST 요청, 인증 필요 없음";
    }

    // JWT 인증 필요 API (GET/POST 모두 허용)
    @GetMapping("/api/protected/hello")
    public String protectedHelloGet() {
        return "Protected Hello! GET 요청, JWT 인증 성공";
    }

    @PostMapping("/api/protected/hello")
    public String protectedHelloPost() {
        return "Protected Hello! POST 요청, JWT 인증 성공";
    }
}
