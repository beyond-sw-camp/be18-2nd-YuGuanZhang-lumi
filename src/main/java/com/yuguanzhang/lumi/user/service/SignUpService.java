package com.yuguanzhang.lumi.user.service;

import com.yuguanzhang.lumi.user.dto.SignupRequestDto;
import com.yuguanzhang.lumi.user.entity.User;

public interface SignUpService {
    void processSignup(SignupRequestDto signupRequestDto);

    User findByEmail(String email);
}
