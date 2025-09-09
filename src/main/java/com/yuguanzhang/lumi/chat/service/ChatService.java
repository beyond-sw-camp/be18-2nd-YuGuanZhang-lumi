package com.yuguanzhang.lumi.chat.service;

import com.yuguanzhang.lumi.chat.dto.ChatRequestDto;
import com.yuguanzhang.lumi.chat.dto.ChatRoomsResponseDto;
import com.yuguanzhang.lumi.chat.dto.ChatsResponseDto;
import com.yuguanzhang.lumi.chat.entity.RoomUser;

import java.util.List;

public interface ChatService {
    List<ChatRoomsResponseDto> getChatRooms(Long userId);

    List<ChatsResponseDto> getChats(Long userId, Long roomId);

    void deleteChat(Long userId, Long roomId, Long chatId);

    RoomUser postChat(ChatRequestDto chatRequestDto, Long userId, Long roomId);
}
