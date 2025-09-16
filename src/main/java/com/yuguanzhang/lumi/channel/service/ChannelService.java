package com.yuguanzhang.lumi.channel.service;

import com.yuguanzhang.lumi.channel.dto.ChannelListResponseDto;
import com.yuguanzhang.lumi.channel.dto.ChannelRequestDto;
import com.yuguanzhang.lumi.channel.dto.ChannelResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChannelService {

    //채널 생성
    ChannelResponseDto createChannel(ChannelRequestDto channelRequestDto);

    //채널 리스트 조회
    Page<ChannelListResponseDto> getChannels(Pageable pageable);

    //채널 상세 조회
    ChannelResponseDto getChannel(Long channelId);

    //채널 수정
    ChannelResponseDto updateChannel(Long channelId, ChannelRequestDto channelRequestDto);

    //채널 삭제
    ChannelResponseDto deleteChannel(Long channelId, Long requestUserId);

}
