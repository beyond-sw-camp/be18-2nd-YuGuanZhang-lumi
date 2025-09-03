package com.yuguanzhang.lumi.chat.repository;

import com.yuguanzhang.lumi.chat.entity.Chat;
import com.yuguanzhang.lumi.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoom_RoomId(Long roomId);

    // 마지막 메세지
    Chat findTopByRoomOrderByCreatedAtDesc(Room room);

    // 안 읽은 메세지 개수
    @Query("SELECT COUNT(m) FROM Chat m WHERE m.room = :room AND m.user.userId != :userId AND m.isRead = false")
    Long countUnreadMessages(Room room, Long userId);

    Optional<Chat> findByRoom_RoomIdAndChatId(Long roomId, Long chatId);
}
