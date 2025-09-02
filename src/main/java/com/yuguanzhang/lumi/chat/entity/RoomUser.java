package com.yuguanzhang.lumi.chat.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RoomUsers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomUser {
    @EmbeddedId
    private RoomUserId roomUserId;
}
