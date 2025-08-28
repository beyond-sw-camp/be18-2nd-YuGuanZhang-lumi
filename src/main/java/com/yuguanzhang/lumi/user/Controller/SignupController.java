package com.yuguanzhang.lumi.user.Controller;

import com.yuguanzhang.lumi.user.Service.UserService;
import com.yuguanzhang.lumi.user.dto.SignupRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class SignupController {

    private final UserService userService;

    /** 브라우저 form 접근 */
    @GetMapping("/sign_up")
    public String signup() {
        return "sign_up"; // templates/sign_up.html
    }

    /** 브라우저 form 제출용 */
    @PostMapping(value = "/sign_up", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String processSignupForm(SignupRequestDto signupRequestDto) {
        userService.processSignup(signupRequestDto);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로
    }

    /** Postman JSON 제출용 */
    @PostMapping(value = "/api/sign_up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String processSignupJson(@RequestBody SignupRequestDto signupRequestDto) {
        System.out.println("11111111111");
        System.out.println(signupRequestDto);
        userService.processSignup(signupRequestDto);
        return "회원가입 성공";
    }
}
