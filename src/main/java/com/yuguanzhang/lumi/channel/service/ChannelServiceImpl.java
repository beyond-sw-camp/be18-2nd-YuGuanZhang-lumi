package com.yuguanzhang.lumi.channel.service;

import com.yuguanzhang.lumi.channel.entity.ChannelUser;
import com.yuguanzhang.lumi.channel.repository.ChannelUserRepository;
import com.yuguanzhang.lumi.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelUserRepository channelUserRepository;

    @Override
    public List<ChannelUser> findByUser(User user) {
        return channelUserRepository.findByUser(user);
    }
}
