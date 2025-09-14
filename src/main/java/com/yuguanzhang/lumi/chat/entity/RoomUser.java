package com.yuguanzhang.lumi.chat.entity;

import com.yuguanzhang.lumi.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RoomUsers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomUser {
    @EmbeddedId
    private RoomUserId roomUserId;

    @MapsId("roomId") // RoomUserId.roomId 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @MapsId("userId") // RoomUserId.userId 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "unread_count")
    private int unreadCount = 0;

    @Column(name = "last_message_content")
    private String lastMessageContent;

    @Column(name = "last_message_time")
    private LocalDateTime lastMessageTime;

    public void increaseUnreadCount() {
        this.unreadCount++;
    }

    public void resetUnreadCount() {
        this.unreadCount = 0;
    }

    public void updateLastMessage(String content, LocalDateTime time) {
        this.lastMessageContent = content;
        this.lastMessageTime = time;
    }
}
