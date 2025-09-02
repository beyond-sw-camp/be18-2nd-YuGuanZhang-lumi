package com.yuguanzhang.lumi.chat.repository;

import com.yuguanzhang.lumi.chat.entity.Message;
import com.yuguanzhang.lumi.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // 마지막 메세지
    Message findTopByRoomOrderByCreatedAtDesc(Room room);

    // 안 읽은 메세지 개수
    @Query("SELECT COUNT(m) FROM Message m WHERE m.room = :room AND m.user.userId = :userId AND m.isRead = false")
    Long countUnreadMessages(Room room, Long userId);

}
