package com.oa.platform.service.impl;


import com.oa.platform.entity.ChatFile;
import com.oa.platform.entity.ChatMessage;
import com.oa.platform.service.ChatMessageService;
import com.oa.platform.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息服务实现类
 * 
 * @author Leo
 * @date 2018/5/16
 */
@Service
public class ChatMessageServiceImpl extends AbstractBaseService<ChatMessage, Long> implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceImpl() {
        super(ChatMessage.class);
    }


    @Override
    public List<ChatMessage> listMessage(String channelId, long maxCreateAt, int limit) {
        return chatMessageRepository.listMessage(channelId, maxCreateAt, limit);
    }

    @Override
    public ChatMessage saveMessage(ChatMessage dto) {
        return null;
    }

    @Override
    public int saveMessage(List<ChatMessage> dtos) {
        return 0;
    }

    @Override
    public int readMessage(String channelId, String userId, short total) {
        return 0;
    }

    @Override
    public int removeMessage(Long messageId, String senderId, String channelId, String toUserId) {
        return 0;
    }

    @Override
    public String saveFile(ChatFile dto) {
        return null;
    }

    @Override
    public ChatMessage getById(Long aLong) {
        return null;
    }
}
