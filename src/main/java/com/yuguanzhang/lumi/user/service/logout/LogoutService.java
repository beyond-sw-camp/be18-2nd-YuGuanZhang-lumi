package com.yuguanzhang.lumi.user.service.logout;

import com.yuguanzhang.lumi.common.jwt.dto.RefreshRequestDto;

public interface LogoutService {
    void logout(RefreshRequestDto request);
}
