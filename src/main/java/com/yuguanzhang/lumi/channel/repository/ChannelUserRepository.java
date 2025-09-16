package com.yuguanzhang.lumi.channel.repository;

import com.yuguanzhang.lumi.channel.entity.ChannelUser;
import com.yuguanzhang.lumi.role.entity.RoleName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelUserRepository extends JpaRepository<ChannelUser, Long> {

    // 특정 채널에 속한 모든 멤버 조회
    List<ChannelUser> findByChannel_ChannelId(Long channelId);

    Page<ChannelUser> findByChannel_ChannelId(Long channelId, Pageable pageable);

    Optional<ChannelUser> findByChannel_ChannelIdAndRole_RoleName(Long channelId,
                                                                  RoleName roleName);

    //이 채널에 이 유저가 속해 있는지 찾는 메소드
    Optional<ChannelUser> findByChannel_ChannelIdAndUserId(Long channelId, Long userId);

    boolean existsByChannel_ChannelIdAndUserId(Long channelId, Long userId);

    // 특정 유저가 속한 모든 채널 조회
    //user 없음

}
