package com.oa.platform.service.impl;

import com.oa.platform.entity.Message;
import com.oa.platform.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends AbstractBaseService<Message, String> implements MessageService {
    public MessageServiceImpl() {
        super(Message.class);
    }
}
