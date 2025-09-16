package com.yuguanzhang.lumi.channel.service;

import com.yuguanzhang.lumi.channel.dto.InvitationRequestDto;
import com.yuguanzhang.lumi.channel.dto.InvitationResponseDto;

public interface InvitationService {

    InvitationResponseDto createInvitation(Long channelId,
            InvitationRequestDto invitationRequestDto);
}
