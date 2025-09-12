package com.yuguanzhang.lumi.user.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;
}
