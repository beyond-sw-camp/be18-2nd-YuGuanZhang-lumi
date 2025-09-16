package com.yuguanzhang.lumi.channel.repository;

import com.yuguanzhang.lumi.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    //채널 이름으로 채널 객체 조회
    Optional<Channel> findByName(String name);


}
