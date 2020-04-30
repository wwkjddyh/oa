package com.oa.platform.service.impl;


import com.oa.platform.entity.FileDTO;
import com.oa.platform.entity.MessageDTO;
import com.oa.platform.service.ChatMessageService;
import com.oa.platform.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 消息服务实现类
 * 
 * @author Leo
 * @date 2018/5/16
 */
public class ChatMessageServiceImpl extends AbstractBaseService<MessageDTO, Long> implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceImpl() {
        super(MessageDTO.class);
    }


    @Override
    public List<MessageDTO> listMessage(String channelId, long maxCreateAt, int limit) {
        return null;
    }

    @Override
    public MessageDTO saveMessage(MessageDTO dto) {
        return null;
    }

    @Override
    public int saveMessage(List<MessageDTO> dtos) {
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
    public String saveFile(FileDTO dto) {
        return null;
    }

    @Override
    public MessageDTO getById(Long aLong) {
        return null;
    }
}
