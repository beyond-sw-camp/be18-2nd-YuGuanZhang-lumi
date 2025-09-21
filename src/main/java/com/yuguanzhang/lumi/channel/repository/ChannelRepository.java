package com.yuguanzhang.lumi.channel.repository;

import com.yuguanzhang.lumi.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    
}
