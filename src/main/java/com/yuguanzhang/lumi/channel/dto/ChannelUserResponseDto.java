package com.yuguanzhang.lumi.channel.dto;

import com.yuguanzhang.lumi.channel.entity.ChannelUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ChannelUserResponseDto {

    private final Long channelId;

    private final Long userId;

    private final String roleName;

    private final boolean notificationEnabled;

    private final String data;

    public static ChannelUserResponseDto fromEntity(ChannelUser channelUser) {
        return ChannelUserResponseDto.builder().channelId(channelUser.getChannel().getChannelId())
                .userId(channelUser.getUserId())
                .roleName(channelUser.getRole().getRoleName().name()) //enum -> String .name()으로 바꿈
                .notificationEnabled(channelUser.isNotificationEnabled())
                .data(channelUser.getData()).build();
    }

}
