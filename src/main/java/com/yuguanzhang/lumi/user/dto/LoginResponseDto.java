package com.yuguanzhang.lumi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String email;
    private String accessToken;
    private String refreshToken;
}
