package com.yuguanzhang.lumi.channel.controller;

import com.yuguanzhang.lumi.channel.dto.ChannelUserRequestDto;
import com.yuguanzhang.lumi.channel.dto.ChannelUserResponseDto;
import com.yuguanzhang.lumi.channel.service.ChannelUserService;
import com.yuguanzhang.lumi.common.dto.BaseResponseDto;
import com.yuguanzhang.lumi.common.dto.PageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelUserController {

    private final ChannelUserService channelUserService;


    //유저 초대 성공시 채널에 추가
    @PostMapping("/{channel_id}/participants")
    public ResponseEntity<BaseResponseDto<ChannelUserResponseDto>> joinChannel(
            @RequestParam String code, @RequestParam Long userId) {
        ChannelUserResponseDto channelUserResponseDto =
                channelUserService.joinChannel(code, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(BaseResponseDto.of(HttpStatus.CREATED, channelUserResponseDto));
    }

    //채널 내의 유저 리스트 조회
    @GetMapping("/{channel_id}/participants")
    public ResponseEntity<PageResponseDto<ChannelUserResponseDto>> getChannelUsers(
            @PathVariable("channel_id") Long channelId, Pageable pageable) {
        Page<ChannelUserResponseDto> channelUsers =
                channelUserService.getChannelUsers(channelId, pageable);

        return ResponseEntity.ok(PageResponseDto.of(HttpStatus.OK, channelUsers));
    }

    //채널 내의 유저 상세 조회
    @GetMapping("/{channel_id}/participants/{user_id}")
    public ResponseEntity<BaseResponseDto<ChannelUserResponseDto>> getChannelUser(
            @PathVariable("channel_id") Long channelId, @PathVariable("user_id") Long userId) {

        ChannelUserResponseDto channelUserResponse =
                channelUserService.getChannelUser(channelId, userId);

        return ResponseEntity.ok(BaseResponseDto.of(HttpStatus.OK, channelUserResponse));
    }

    //채널 내의 유저 정보 수정
    @PutMapping("/{channel_id}/participants/{user_id}")
    public ResponseEntity<BaseResponseDto<ChannelUserResponseDto>> updateChannelUserData(
            @PathVariable("channel_id") Long channelId, @PathVariable("user_id") Long userId,
            @Valid @RequestBody ChannelUserRequestDto channelUserRequestDto) {

        ChannelUserResponseDto channelUserResponse =
                channelUserService.updateChannelUserData(channelId, userId, channelUserRequestDto);

        return ResponseEntity.ok(BaseResponseDto.of(HttpStatus.OK, channelUserResponse));
    }

    //채널 내의 유저 탈퇴
    @DeleteMapping("/{channel_id}/participants/{user_id}")
    public ResponseEntity<BaseResponseDto<ChannelUserResponseDto>> deleteChannelUser(
            @PathVariable("channel_id") Long channelId, @PathVariable("user_id") Long userId,
            //아직 유저 없어서 유저로 못넘기고 파라미터로 일단 넘겨서 테스트 했습니다.
            @RequestParam Long requestUserId) {

        ChannelUserResponseDto channelUserResponse =
                channelUserService.leaveChannel(channelId, userId, requestUserId);

        return ResponseEntity.ok(BaseResponseDto.of(HttpStatus.OK, channelUserResponse));
    }

}
