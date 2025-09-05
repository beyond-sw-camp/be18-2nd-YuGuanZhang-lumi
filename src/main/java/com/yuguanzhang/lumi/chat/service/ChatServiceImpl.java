package com.yuguanzhang.lumi.chat.service;

import com.yuguanzhang.lumi.chat.dto.ChatRequestDto;
import com.yuguanzhang.lumi.chat.dto.ChatRoomsResponseDto;
import com.yuguanzhang.lumi.chat.dto.ChatsResponseDto;
import com.yuguanzhang.lumi.chat.entity.Chat;
import com.yuguanzhang.lumi.chat.entity.Room;
import com.yuguanzhang.lumi.chat.entity.RoomUser;
import com.yuguanzhang.lumi.chat.repository.ChatRepository;
import com.yuguanzhang.lumi.chat.repository.RoomRepository;
import com.yuguanzhang.lumi.chat.repository.RoomUserRepository;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final RoomUserRepository roomUserRepository;
    private final ChatRepository chatRepository;


    @Override
    public List<ChatRoomsResponseDto> getChatRooms(Long userId) {

        // 사용자가 속한 방 목록 조회
        List<RoomUser> roomUsers = roomUserRepository.findByRoomUserId_User_UserId(userId);

        List<ChatRoomsResponseDto> result = new ArrayList<>();

        for (RoomUser ru : roomUsers) {
            Room room = ru.getRoomUserId().getRoom();

            // 채팅방의 상대
            RoomUser senderRoomUser =
                    roomUserRepository.findByRoomUserId_Room_RoomIdAndRoomUserId_User_UserIdNot(
                                    room.getRoomId(), userId)
                            .orElseThrow(() -> new NoSuchElementException("상대방이 존재하지 않습니다."));

            // 상대방 정보
            User sender = senderRoomUser.getRoomUserId().getUser();

            ChatRoomsResponseDto dto =
                    ChatRoomsResponseDto.builder().roomId(room.getRoomId()).roomName(room.getName())
                            .opponentId(sender.getUserId()).opponentName(sender.getName())
                            .lastMessageTime(ru.getLastMessageTime())
                            .lastMessage(ru.getLastMessageContent())
                            .unreadMessageCount(ru.getUnreadCount()).build();

            result.add(dto);
        }
        return result;

    }

    @Override
    @Transactional
    public List<ChatsResponseDto> getChats(Long userId, Long roomId) {
        List<Chat> chatList = chatRepository.findByRoom_RoomId(roomId);

        chatList.forEach(chat -> {
            if (Boolean.FALSE.equals(chat.getIsRead()) && !chat.getUser().getUserId()
                    .equals(userId)) {
                chat.updateIsRead();
            }
        });

        // RoomUser unreadCount 초기화
        RoomUser roomUser =
                roomUserRepository.findByRoomUserId_Room_RoomIdAndRoomUserId_User_UserId(roomId,
                                userId)
                        .orElseThrow(() -> new NoSuchElementException("해당 채팅방에 사용자를 찾을 수 없습니다."));
        roomUser.resetUnreadCount();
        roomUserRepository.save(roomUser);

        return chatList.stream()
                .map(chat -> ChatsResponseDto.builder().roomId(chat.getRoom().getRoomId())
                        .chatId(chat.getChatId()).messageType(chat.getMessageType())
                        .senderId(chat.getUser().getUserId()).senderName(chat.getUser().getName())
                        .message(chat.getContent()).createdAt(chat.getCreatedAt()).build())
                .toList();
    }

    @Override
    @Transactional
    public void deleteChat(Long userId, Long roomId, Long chatId) {
        // 채팅 메세지 조회 (에러처리 필요)
        Chat chat = chatRepository.findByRoom_RoomIdAndChatId(roomId, chatId)
                .orElseThrow(() -> new EntityNotFoundException("해당 채팅 메세지를 찾을 수 없습니다."));

        // 커스텀 에러 처리 필요
        // if (!chat.getUser().getUserId().equals(userId)) {
        //     throw new CustomError("이 메세지를 삭제할 권한이 없습니다.");
        // }

        // 삭제했을 때의 마지막 메세지 및 안 읽은 메세지 개수 업데이트 필요

        chatRepository.delete(chat);
    }
}
