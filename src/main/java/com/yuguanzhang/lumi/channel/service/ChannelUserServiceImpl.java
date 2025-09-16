package com.yuguanzhang.lumi.channel.service;

import com.yuguanzhang.lumi.channel.dto.ChannelUserRequestDto;
import com.yuguanzhang.lumi.channel.dto.ChannelUserResponseDto;
import com.yuguanzhang.lumi.channel.entity.ChannelUser;
import com.yuguanzhang.lumi.channel.entity.Invitation;
import com.yuguanzhang.lumi.channel.repository.ChannelRepository;
import com.yuguanzhang.lumi.channel.repository.ChannelUserRepository;
import com.yuguanzhang.lumi.channel.repository.InvitationRepository;
import com.yuguanzhang.lumi.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChannelUserServiceImpl implements ChannelUserService {

    private final ChannelUserRepository channelUserRepository;
    private final InvitationRepository invitationRepository;


    @Override
    @Transactional
    public ChannelUserResponseDto joinChannel(String code, User user) {
        //초대 조회
        Invitation invitation = invitationRepository.findByInvitationCode(code)
                                                    .orElseThrow(() -> new EntityNotFoundException(
                                                            "초대코드가 존재하지 않습니다."));
        //만료, 사용 여부 검증
        if (invitation.isExpired() || invitation.isUsed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "초대가 만료되었거나 이미 사용되었습니다.");
        }

        //이미 채널에 참여 중인지 검증
        boolean exist = channelUserRepository.existsByChannel_ChannelIdAndUser_UserId(
                invitation.getChannel()
                          .getChannelId(), user.getUserId());
        if (exist) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 채널에 참가한 사용자입니다.");
        }

        //채널 유저 객체 생성 == 채널에 참가
        ChannelUser channelUser = ChannelUser.builder()
                                             .channel(invitation.getChannel())
                                             .user(user)
                                             .role(invitation.getRole())
                                             .notificationEnabled(true)
                                             .build();
        //db에 생성한 객체 저장
        channelUserRepository.save(channelUser);
        //초대를 사용된 초대로 변경
        invitation.markUsed();

        return ChannelUserResponseDto.fromEntity(channelUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChannelUserResponseDto> getChannelUsers(Long channelId, Pageable pageable) {
        Page<ChannelUser> channelUsers =
                channelUserRepository.findByChannel_ChannelId(channelId, pageable);

        return channelUsers.map(ChannelUserResponseDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ChannelUserResponseDto getChannelUser(Long channelId, UUID userId) {
        ChannelUser channelUser =
                channelUserRepository.findByChannel_ChannelIdAndUser_UserId(channelId, userId)
                                     .orElseThrow(() -> new EntityNotFoundException(
                                             "채널에 속하지 않은 사용자입니다."));

        return ChannelUserResponseDto.fromEntity(channelUser);
    }

    @Override
    @Transactional
    public ChannelUserResponseDto updateChannelUserData(Long channelId, User user,
                                                        ChannelUserRequestDto channelUserRequestDto) {

        ChannelUser channelUser =
                channelUserRepository.findByChannel_ChannelIdAndUser_UserId(channelId,
                                                                            user.getUserId())
                                     .orElseThrow(() -> new EntityNotFoundException(
                                             "채널에 속하지 않은 사용자입니다."));


        //update 메소드
        channelUser.updateData(channelUserRequestDto.getData());
        channelUser.updateNotificationEnabled(channelUserRequestDto.isNotificationEnabled());

        return ChannelUserResponseDto.fromEntity(channelUser);
    }

    @Override
    @Transactional
    public ChannelUserResponseDto leaveChannel(Long channelId, User user) {

        ChannelUser channelUser =
                channelUserRepository.findByChannel_ChannelIdAndUser_UserId(channelId,
                                                                            user.getUserId())
                                     .orElseThrow(() -> new EntityNotFoundException(
                                             "채널에 속하지 않은 사용자입니다."));


        channelUserRepository.delete(channelUser);

        return ChannelUserResponseDto.fromEntity(channelUser);
    }

    private void validateSelf(ChannelUser channelUser, UUID requestUserId) {
        if (!channelUser.getUser()
                        .getUserId()
                        .equals(requestUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 수정/탈퇴할 수 있습니다.");
        }
    }
}
