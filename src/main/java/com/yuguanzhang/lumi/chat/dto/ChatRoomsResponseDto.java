package com.yuguanzhang.lumi.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ChatRoomsResponseDto {
    private final Long roomId;
    private final String roomName;
    private final Long opponentId;
    private final String opponentName;
    private final LocalDateTime lastMessageTime;
    private final String lastMessage;
    private final Long unreadMessageCount;
}
