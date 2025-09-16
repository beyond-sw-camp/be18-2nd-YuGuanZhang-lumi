package com.yuguanzhang.lumi.channel.dto;

import com.yuguanzhang.lumi.channel.entity.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class ChannelResponseDto {

    private final Long channelId;

    private final String name;

    private final String subject;

    private final UUID requestUserId;

    public static ChannelResponseDto fromEntity(Channel channel, UUID requestUserId) {
        return ChannelResponseDto.builder()
                                 .channelId(channel.getChannelId())
                                 .name(channel.getName())
                                 .subject(channel.getSubject())
                                 .requestUserId(requestUserId)
                                 .build();
    }

    public static ChannelResponseDto fromEntity(Channel channel) {
        return ChannelResponseDto.builder()
                                 .channelId(channel.getChannelId())
                                 .name(channel.getName())
                                 .subject(channel.getSubject())
                                 .build();
    }
}
