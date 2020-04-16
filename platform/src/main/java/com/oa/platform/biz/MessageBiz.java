package com.oa.platform.biz;

import com.oa.platform.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息
 * @author jianbo
 * @create 2020/04/16
 */
@Component
public class MessageBiz extends BaseBiz {

    @Autowired
    private MessageService messageService;
}
