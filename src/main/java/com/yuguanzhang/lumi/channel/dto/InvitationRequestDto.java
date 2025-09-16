package com.yuguanzhang.lumi.channel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class InvitationRequestDto {

    private UUID requestUserId;

    private Long roleId;

}
