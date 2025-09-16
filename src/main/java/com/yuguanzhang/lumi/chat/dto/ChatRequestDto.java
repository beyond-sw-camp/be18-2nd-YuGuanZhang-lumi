package com.yuguanzhang.lumi.chat.dto;

import com.yuguanzhang.lumi.chat.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ChatRequestDto {
    private final MessageType messageType;

    private final String message;

    private final Long receiverId;

}
