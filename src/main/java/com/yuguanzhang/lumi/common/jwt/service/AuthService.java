package com.yuguanzhang.lumi.common.jwt.service;

import com.yuguanzhang.lumi.common.jwt.dto.LoginRequestDto;
import com.yuguanzhang.lumi.common.jwt.dto.LoginResponseDto;


public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);
}
