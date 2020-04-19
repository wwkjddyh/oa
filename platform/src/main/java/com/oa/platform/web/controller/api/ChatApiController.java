package com.oa.platform.web.controller.api;

import com.oa.platform.biz.MessageBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聊天API接口
 * @author jianbo.feng
 * @create 2020/04/19
 */
@RestController
@RequestMapping("/api/chat")
public class ChatApiController {

    @Autowired
    private MessageBiz messageBiz;
}
