package com.yuguanzhang.lumi.channel.controller;

import com.yuguanzhang.lumi.channel.dto.InvitationRequestDto;
import com.yuguanzhang.lumi.channel.dto.InvitationResponseDto;
import com.yuguanzhang.lumi.channel.service.InvitationService;
import com.yuguanzhang.lumi.common.dto.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping("/{channel_id}/invitations")
    public ResponseEntity<BaseResponseDto<InvitationResponseDto>> createInvitation(
            @PathVariable("channel_id") Long channelId,
            @RequestBody InvitationRequestDto invitationRequestDto) {

        InvitationResponseDto invitationResponseDto =
                invitationService.createInvitation(channelId, invitationRequestDto);
   
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(BaseResponseDto.of(HttpStatus.CREATED, invitationResponseDto));
    }
}
