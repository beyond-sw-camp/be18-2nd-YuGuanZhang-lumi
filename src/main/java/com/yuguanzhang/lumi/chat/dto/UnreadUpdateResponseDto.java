package com.yuguanzhang.lumi.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class UnreadUpdateResponseDto {
    private final Long roomId;
    private final int unreadCount;
    private final String lastMessage;
    private final LocalDateTime lastMessageTime;
    private final Long opponentId;
}
