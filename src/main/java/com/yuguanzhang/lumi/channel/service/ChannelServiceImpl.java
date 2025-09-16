package com.yuguanzhang.lumi.channel.service;

import com.yuguanzhang.lumi.channel.dto.ChannelListResponseDto;
import com.yuguanzhang.lumi.channel.dto.ChannelRequestDto;
import com.yuguanzhang.lumi.channel.dto.ChannelResponseDto;
import com.yuguanzhang.lumi.channel.entity.Channel;
import com.yuguanzhang.lumi.channel.entity.ChannelUser;
import com.yuguanzhang.lumi.channel.repository.ChannelRepository;
import com.yuguanzhang.lumi.channel.repository.ChannelUserRepository;
import com.yuguanzhang.lumi.common.service.RoleAuthorizationService;
import com.yuguanzhang.lumi.role.entity.Role;
import com.yuguanzhang.lumi.role.entity.RoleName;
import com.yuguanzhang.lumi.role.repositiry.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    private final ChannelUserRepository channelUserRepository;

    private final RoleRepository roleRepository;

    private final RoleAuthorizationService roleAuthorizationService;

    @Override
    @Transactional
    public ChannelResponseDto createChannel(ChannelRequestDto channelRequestDto) {
        //Channel 객체 생성
        Channel channel = channelRequestDto.toEntity(); //dto에 메소드 만들어서 사용
        channelRepository.save(channel);

        //생성한 사람에게 TUTOR 역할 부여하기 위해 TUTOR인 Role 만들기
        Role tutorRole = roleRepository.findByRoleName(RoleName.TUTOR)
                                       .orElseThrow(() -> new IllegalStateException(
                                               "TUTOR 역할이 존재하지 않습니다."));

        //ChannelUser 객체 생성
        ChannelUser channelUser = ChannelUser.builder()
                                             .channel(channel)
                                             .userId(channelRequestDto.getRequestUserId())
                                             .notificationEnabled(true)
                                             .role(tutorRole)
                                             .build();

        //db에 저장(insert)
        channelUserRepository.save(channelUser);

        return ChannelResponseDto.fromEntity(channel, channelRequestDto.getRequestUserId());
    }

    @Override
    @Transactional(readOnly = true) //조회 전용 트랜잭션
    public Page<ChannelListResponseDto> getChannels(Pageable pageable) {
        //채널 가져오기
        Page<Channel> channels = channelRepository.findAll(pageable);

        return channels.map(ChannelListResponseDto::fromEntity);
    }

    @Override
    public ChannelResponseDto getChannel(Long channelId) {

        Channel channel = channelRepository.findById(channelId)
                                           .orElseThrow(() -> new EntityNotFoundException(
                                                   "채널이 존재하지 않습니다. channelId= " + channelId));

        return ChannelResponseDto.fromEntity(channel);
    }

    @Override
    @Transactional
    public ChannelResponseDto updateChannel(Long channelId, ChannelRequestDto channelRequestDto) {

        //권한 체크
        roleAuthorizationService.checkTutor(channelId, channelRequestDto.getRequestUserId());

        //수정할 채널 가져오기
        Channel channel = channelRepository.findById(channelId)
                                           .orElseThrow(() -> new EntityNotFoundException(
                                                   "채널이 존재하지 않습니다. channelId= " + channelId));

        //채널 수정
        channel.updateName(channelRequestDto.getName());
        channel.updateSubject(channelRequestDto.getSubject());

        return ChannelResponseDto.fromEntity(channel);
    }

    @Override
    public ChannelResponseDto deleteChannel(Long channelId, Long requestUserId) {

        //권한 체크
        roleAuthorizationService.checkTutor(channelId, requestUserId);

        //삭제할 채널 가져오기
        Channel channel = channelRepository.findById(channelId)
                                           .orElseThrow(() -> new EntityNotFoundException(
                                                   "채널이 존재하지 않습니다. channelId= " + channelId));

        //채널 삭제
        channelRepository.delete(channel);

        return ChannelResponseDto.fromEntity(channel);
    }

}
