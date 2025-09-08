package com.yuguanzhang.lumi.common.jwt.service.logout;

import com.yuguanzhang.lumi.common.jwt.dto.RefreshRequestDto;

public interface LogoutService {
    void logout(RefreshRequestDto request);
}
