package com.yuguanzhang.lumi.common.jwt.service.login;

import com.yuguanzhang.lumi.common.jwt.dto.LoginRequestDto;
import com.yuguanzhang.lumi.common.jwt.dto.LoginResponseDto;


public interface LoginService {
    LoginResponseDto login(LoginRequestDto request);
}
