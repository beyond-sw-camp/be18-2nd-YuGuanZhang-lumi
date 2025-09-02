package com.yuguanzhang.lumi.channel.repository;

import com.yuguanzhang.lumi.channel.entity.ChannelUser;
import com.yuguanzhang.lumi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChannelUserRepository extends JpaRepository<ChannelUser, Long> {
    // 내가 속한 채널들
    List<ChannelUser> findByUser(User user);

    // 사용자가 존재하는 채널의 모든 사용자 리스트업
    @Query("SELECT DISTINCT cu.user " +
            "FROM ChannelUser cu " +
            "WHERE cu.channel IN (SELECT c.channel FROM ChannelUser c WHERE c.user = :me) " +
            "AND cu.user <> :me"
    )
    List<User> findAllUsersInMyChannels(User me);
}
