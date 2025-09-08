package com.yuguanzhang.lumi.common.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequestDto {
    private String refreshToken;
}
