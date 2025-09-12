package com.yuguanzhang.lumi.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class ChatRoomsResponseDto {
    private final Long roomId;
    private final String roomName;
    private final UUID opponentId;
    private final String opponentName;
    private final LocalDateTime lastMessageTime;
    private final String lastMessage;
    private final int unreadMessageCount;
}
