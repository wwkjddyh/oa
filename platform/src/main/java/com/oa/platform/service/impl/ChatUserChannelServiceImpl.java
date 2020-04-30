package com.oa.platform.service.impl;


import com.oa.platform.entity.ChatUserChannel;
import com.oa.platform.service.ChatUserChannelService;
import com.oa.platform.repository.ChatChannelRepository;
import com.oa.platform.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户频道服务实现类
 * 
 * @author Lining
 * @date 2018/4/20
 */
public class ChatUserChannelServiceImpl extends AbstractBaseService<ChatUserChannel, String> implements ChatUserChannelService {

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Autowired
    private ChatChannelRepository chatChannelRepository;

    public ChatUserChannelServiceImpl() {
        super(ChatUserChannel.class);
    }

    @Override
    public List<ChatUserChannel> listUserChannel(String userId, String type, int limit) {
        return null;
    }

    @Override
    public ChatUserChannel get(String userId, String channelId) {
        return null;
    }

    @Override
    public int updateDisplayName(String channelId, String userId, String displayName) {
        return 0;
    }

    @Override
    public int hideChannel(String userId, String channelId) {
        return 0;
    }

    @Override
    public List<ChatUserChannel> listByName(String userId, String name, String type) {
        return null;
    }
}