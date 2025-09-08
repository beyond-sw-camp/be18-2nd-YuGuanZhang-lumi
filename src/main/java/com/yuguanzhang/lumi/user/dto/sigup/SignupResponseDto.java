package com.yuguanzhang.lumi.user.dto.sigup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupResponseDto {
    private String message;
    private String email;
    private String name;
}
