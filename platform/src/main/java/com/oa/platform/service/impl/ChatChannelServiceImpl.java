package com.oa.platform.service.impl;


import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.ChatChannel;
import com.oa.platform.entity.ChatChannelList;
import com.oa.platform.entity.ChatChannelMember;
import com.oa.platform.service.ChatChannelService;
import com.oa.platform.repository.ChatChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 频道服务实现类
 * 
 * @author Leo
 * @date 2018/4/3
 */
@Service
public class ChatChannelServiceImpl extends AbstractBaseService<ChatChannel, String> implements ChatChannelService {

    @Autowired
    private ChatChannelRepository chatChannelRepository;

    public ChatChannelServiceImpl() {
        super(ChatChannel.class);
    }


    @Override
    public List<ChatChannelList> listChannel(Map<String, Object> parameters, int limit) {
        return null;
    }

    @Override
    public List<ChatChannelList> listGroupChannel(Map<String, Object> parameters, int limit) {
        return null;
    }

    @Override
    public ChatChannel saveChannel(ChatChannel dto, String creatorNickname) {
        return null;
    }

    @Override
    public boolean isAdmin(String userId, String channelId) {
        return false;
    }

    @Override
    public int updateName(String channelId, String name) {
        return 0;
    }

    @Override
    public int updatePurpose(String channelId, String purpose) {
        return 0;
    }

    @Override
    public int addMember(String channelId, String[] userIds, String[] userNicknames, String admin) {
        return 0;
    }

    @Override
    public int removeMember(String channelId, String memberId, String memberNickname, String admin) {
        return 0;
    }

    @Override
    public PageInfo<ChatChannelMember> listMember(String channelId, String username, int limit, int offset) {
        return null;
    }

    @Override
    public int changeAdmin(String channelId, String memberId, boolean isAdmin) {
        return 0;
    }

    @Override
    public int leaveChannel(String channelId, String memberId, String memberNickname) {
        return 0;
    }

    @Override
    public int removeChannel(String channelId, String adminId) {
        return 0;
    }
}
