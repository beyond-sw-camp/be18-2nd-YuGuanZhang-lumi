package com.yuguanzhang.lumi.user.controller;

import com.yuguanzhang.lumi.common.dto.BaseResponseDto;
import com.yuguanzhang.lumi.user.dto.deleted.DeletedRequestDto;
import com.yuguanzhang.lumi.user.dto.deleted.DeletedResponseDto;
import com.yuguanzhang.lumi.user.service.deleted.DeletedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeletedController {
    private final DeletedService deletedService;

    // 찾아보니깐 지금 상황에서는 상태 변화를 하는거라서 Post매핑이 맞는거 같음
    // 리소스를 직접 삭제하는게 아니라서 DELETE는 아닌거 같음
    @PostMapping("/api/deleted")
    public BaseResponseDto<DeletedResponseDto> deleted(@RequestBody DeletedRequestDto request) {
        DeletedResponseDto response = deletedService.deleted(request);

        return BaseResponseDto.of(HttpStatus.OK, response);
    }

}
