package com.yuguanzhang.lumi.chat.service;

import com.yuguanzhang.lumi.chat.dto.ChatRoomsResponseDto;
import com.yuguanzhang.lumi.chat.entity.Chat;
import com.yuguanzhang.lumi.chat.entity.Room;
import com.yuguanzhang.lumi.chat.entity.RoomUser;
import com.yuguanzhang.lumi.chat.repository.ChatRepository;
import com.yuguanzhang.lumi.chat.repository.RoomUserRepository;
import com.yuguanzhang.lumi.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final RoomUserRepository roomUserRepository;
    private final ChatRepository messageRepository;


    @Override
    public List<ChatRoomsResponseDto> getChatRooms(Long userId) {

        // 사용자가 속한 방 목록 조회
        List<RoomUser> roomUsers = roomUserRepository.findByRoomUserId_User_UserId(userId);

        List<ChatRoomsResponseDto> result = new ArrayList<>();

        for(RoomUser ru: roomUsers) {
            Room room = ru.getRoomUserId().getRoom();

            // 채팅방의 상대방
            RoomUser senderRoomUser = roomUserRepository.findByRoomUserId_Room_RoomIdAndRoomUserId_User_UserIdNot(room.getRoomId(), userId).getFirst();

            // 상대방 정보
            User sender = senderRoomUser.getRoomUserId().getUser();

            Chat lastMessage = messageRepository.findTopByRoomOrderByCreatedAtDesc(room);

            Long unreadCount = messageRepository.countUnreadMessages(room, userId);

            ChatRoomsResponseDto dto = ChatRoomsResponseDto.builder()
                    .roomId(room.getRoomId())
                    .roomName(room.getName())
                    .opponentId(sender.getUserId())
                    .opponentName(sender.getName())
                    .lastMessageTime(lastMessage != null ? lastMessage.getCreatedAt() : null)
                    .lastMessage(lastMessage !=null ? lastMessage.getContent() : null)
                    .unreadMessageCount(unreadCount)
                    .build();

            result.add(dto);
        }
        return result;

    }
}
