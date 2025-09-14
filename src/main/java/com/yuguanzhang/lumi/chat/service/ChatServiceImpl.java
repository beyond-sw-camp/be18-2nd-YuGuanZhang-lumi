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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final RoomUserRepository roomUserRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    @Override
    public List<ChatRoomsResponseDto> getChatRooms(UUID userId) {

        // 사용자가 속한 방 목록 조회
        List<RoomUser> roomUsers = roomUserRepository.findByRoomUserId_UserId(userId);

        List<ChatRoomsResponseDto> result = new ArrayList<>();

        for (RoomUser ru : roomUsers) {
            Room room = ru.getRoom();

            // 채팅방의 상대
            RoomUser senderRoomUser =
                    roomUserRepository.findByRoomUserId_RoomIdAndRoomUserId_UserIdNot(
                                    room.getRoomId(), userId)
                            .orElseThrow(() -> new NoSuchElementException("상대방이 존재하지 않습니다."));

            // 상대방 정보
            User sender = senderRoomUser.getUser();

            ChatRoomsResponseDto dto = ChatRoomsResponseDto.fromEntity(room, sender, ru);

            result.add(dto);
        }
        return result;

    }

    @Override
    @Transactional
    public List<ChatsResponseDto> getChats(UUID userId, Long roomId) {
        List<Chat> chatList = chatRepository.findByRoom_RoomId(roomId);

        chatList.forEach(chat -> {
            if (Boolean.FALSE.equals(chat.getIsRead()) && !chat.getUser().getUserId()
                    .equals(userId)) {
                chat.updateIsRead();
            }
        });

        // RoomUser unreadCount 초기화
        RoomUser roomUser =
                roomUserRepository.findByRoomUserId_RoomIdAndRoomUserId_UserId(roomId, userId)
                        .orElseThrow(() -> new NoSuchElementException("해당 채팅방에 사용자를 찾을 수 없습니다."));
        roomUser.resetUnreadCount();
        roomUserRepository.save(roomUser);

        return chatList.stream().map(ChatsResponseDto::fromEntity).toList();
    }

    @Override
    @Transactional
    public void deleteChat(UUID userId, Long roomId, Long chatId) {
        // 채팅 메세지 조회 (에러처리 필요)
        Chat chat = chatRepository.findByRoom_RoomIdAndChatId(roomId, chatId)
                .orElseThrow(() -> new EntityNotFoundException("해당 채팅 메세지를 찾을 수 없습니다."));

        // 커스텀 에러 처리 필요


        // 삭제했을 때의 마지막 메세지 및 안 읽은 메세지 개수 업데이트 필요

        chatRepository.delete(chat);
    }

    @Override
    @Transactional
    public RoomUser postChat(ChatRequestDto chatRequestDto, UUID userId, Long roomId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방 정보를 찾을 수 없습니다."));

        Chat chat = chatRequestDto.toEntity(room, user);

        chatRepository.save(chat);

        log.info("chat: {}", chat);

        // 받는 사람 RoomUser의 lastMessage와 안읽은 메세지 수 업데이트
        RoomUser receiverRoomUser =
                roomUserRepository.findByRoomUserId_RoomIdAndRoomUserId_UserIdNot(roomId, userId)
                        .orElseThrow(() -> new NoSuchElementException("상대방이 존재하지 않습니다."));

        receiverRoomUser.updateLastMessage(chat.getContent(), chat.getCreatedAt());
        receiverRoomUser.increaseUnreadCount();

        // 보낸 사람 RoomUser의 lastMessage 업데이트
        RoomUser senderRoomUser =
                roomUserRepository.findByRoomUserId_RoomIdAndRoomUserId_UserId(roomId, userId)
                        .orElseThrow();
        senderRoomUser.updateLastMessage(chat.getContent(), chat.getCreatedAt());

        return receiverRoomUser;
    }

}
