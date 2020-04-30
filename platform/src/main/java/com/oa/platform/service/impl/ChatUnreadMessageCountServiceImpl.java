package com.oa.platform.service.impl;


import com.oa.platform.entity.UserChannelDTO;
import com.oa.platform.service.ChatUnreadMessageCountService;
import com.oa.platform.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 未读消息数量
 * @author Administrator
 *
 */
public class ChatUnreadMessageCountServiceImpl extends AbstractBaseService<UserChannelDTO, String> implements ChatUnreadMessageCountService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatUnreadMessageCountServiceImpl() {
        super(UserChannelDTO.class);
    }

    @Override
    public int batchSaveUnreadMessageCount(String[] userIds, String channelId, short total) {
        return 0;
    }

    @Override
    public int updateUnreadMessageCount(String userId, String channelId, short total) {
        return 0;
    }

    @Override
    public int batchUpdateUnreadMessageCount(String[] userIds, String channelId, short total) {
        return 0;
    }

    @Override
    public int batchIncreaseUnreadMessageCount(String[] userIds, String channelId, short quantity) {
        return 0;
    }

    @Override
    public int increaseUnreadMessageCount(String userId, String channelId, short quantity) {
        return 0;
    }
}
