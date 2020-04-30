package com.oa.platform.web.controller.api;

import com.oa.platform.biz.ChatChannelBiz;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 即时聊天：频道接口
 * @author Feng
 * @date 2020/04/30
 */
@RestController
@RequestMapping("/api/chat/channel")
public class ChatChannelApiController extends BaseController {

    @Autowired
    private ChatChannelBiz chatChannelBiz;
}
