package com.yuguanzhang.lumi.channel.dto;

import com.yuguanzhang.lumi.channel.entity.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
// 리스트 조회에서는 채널 생성자가 필요 없어서 dto 따로 만듦
public class ChannelListResponseDto {
    private final Long channelId;

    private final String name;

    private final String subject;

    public static ChannelListResponseDto fromEntity(Channel channel) {
        return ChannelListResponseDto.builder().channelId(channel.getChannelId())
                .name(channel.getName()).subject(channel.getSubject()).build();

    }

}
