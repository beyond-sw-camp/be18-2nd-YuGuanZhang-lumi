package com.yuguanzhang.lumi.channel.service;

import com.yuguanzhang.lumi.channel.entity.ChannelUser;
import com.yuguanzhang.lumi.user.entity.User;

import java.util.List;

public interface ChannelService {
    List<ChannelUser> findByUser(User user);
}
