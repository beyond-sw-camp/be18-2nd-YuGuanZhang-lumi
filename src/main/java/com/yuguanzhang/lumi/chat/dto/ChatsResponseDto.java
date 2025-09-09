package com.yuguanzhang.lumi.chat.dto;


import com.yuguanzhang.lumi.chat.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ChatsResponseDto {
    private final Long roomId;
    private final Long chatId;
    private final MessageType messageType;
    private final Long senderId;
    private final String senderName;
    private final String message;
    private final LocalDateTime createdAt;
    private final List<String> files; // 추후 수정 필요
}
