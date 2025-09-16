package com.yuguanzhang.lumi.common.service;

import com.yuguanzhang.lumi.channel.entity.ChannelUser;
import com.yuguanzhang.lumi.channel.repository.ChannelUserRepository;
import com.yuguanzhang.lumi.role.entity.RoleName;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleAuthorizationService {

    private final ChannelUserRepository channelUserRepository;

    //튜터 검증
    public void checkTutor(Long channelId, UUID userId) {

        ChannelUser channelUser =
                channelUserRepository.findByChannel_ChannelIdAndUser_UserId(channelId, userId)
                                     .orElseThrow(() -> new EntityNotFoundException(
                                             "채널에 속하지 않은 사용자입니다."));

        if (channelUser.getRole()
                       .getRoleName() != RoleName.TUTOR) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "튜터만 이 작업을 수행할 수 있습니다.");
        }
    }

}
