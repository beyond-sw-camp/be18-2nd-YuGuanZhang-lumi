package com.yuguanzhang.lumi.chat.service;

import com.yuguanzhang.lumi.chat.dto.ChatRoomsResponseDto;

import java.util.List;

public interface ChatService {
    List<ChatRoomsResponseDto> getChatRooms(Long userId);
}
