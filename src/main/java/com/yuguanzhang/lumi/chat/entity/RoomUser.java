package com.yuguanzhang.lumi.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
