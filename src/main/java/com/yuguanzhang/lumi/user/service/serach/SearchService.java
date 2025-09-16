package com.yuguanzhang.lumi.user.service.serach;

import com.yuguanzhang.lumi.user.dto.search.SearchRequestDto;
import com.yuguanzhang.lumi.user.dto.search.SearchResoponseDto;

import java.util.UUID;

public interface SearchService {
    SearchResoponseDto searchUser(UUID userId);
}
