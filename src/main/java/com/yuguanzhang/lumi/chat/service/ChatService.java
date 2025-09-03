package com.yuguanzhang.lumi.chat.service;

import com.yuguanzhang.lumi.chat.dto.ChatRoomsResponseDto;
import com.yuguanzhang.lumi.chat.dto.ChatsResponseDto;

import java.util.List;

public interface ChatService {
    List<ChatRoomsResponseDto> getChatRooms(Long userId);

    List<ChatsResponseDto> getChats(Long roomId);
}
