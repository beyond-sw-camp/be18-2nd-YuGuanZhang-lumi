package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.common.dto.BaseResponseDto;
import com.yuguanzhang.lumi.user.dto.UserDetailsDto;
import com.yuguanzhang.lumi.user.dto.search.SearchResoponseDto;
import com.yuguanzhang.lumi.user.service.serach.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/user/profile")
    public BaseResponseDto<SearchResoponseDto> searchUserProfile(
            @AuthenticationPrincipal UserDetailsDto userDetails) {
        UUID userId = userDetails.getUser()
                                 .getUserId();
        SearchResoponseDto responseDto = searchService.getProfile(userId);
        return BaseResponseDto.of(HttpStatus.OK, responseDto);
    }

}
// final를 붙인 이유가 '읽기 전용으로만 쓰겠다' 라는걸 보여주기 위함 이라는데 없어도 상관없을 듯
