package com.yuguanzhang.lumi.channel.entity;

import com.yuguanzhang.lumi.channel.entity.enums.RoleType;
import com.yuguanzhang.lumi.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.NoArgsConstructor;

@Entity
@Table(name="channel_users",
    uniqueConstraints = {
        @UniqueConstraint(
                name="unique_user_channel_role_name",
                columnNames = {"user_id", "channel_id","role_name" }
        )
    })
@NoArgsConstructor
public class ChannelUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="channel_user_id")
    private Long channelUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="channel_id")
    private Channel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleType roleName;

    @Column(name="info")
    private String info;

}
