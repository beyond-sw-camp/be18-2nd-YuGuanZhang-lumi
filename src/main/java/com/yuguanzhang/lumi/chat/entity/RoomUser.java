package com.yuguanzhang.lumi.chat.entity;

import com.yuguanzhang.lumi.user.entity.User;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
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
