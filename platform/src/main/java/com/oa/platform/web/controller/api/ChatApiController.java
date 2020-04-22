package com.oa.platform.web.controller.api;

import com.oa.platform.biz.MessageBiz;
import com.oa.platform.common.StatusCode;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 聊天API
 * @author jianbo.feng
 * @create 2020/04/22
 */
@RestController
@RequestMapping("/api/chat")
public class ChatApiController extends BaseController {

    @Autowired
    private MessageBiz messageBiz;

    /**
     * 查询用户和各个好友交流的最新一条记录
     * @param userId 用户ID（必须）
     * @return
     */
    @GetMapping("findFriendsLastestMessage")
    public Map<String, Object> findFriendsLastestMessage(@RequestParam  String userId) {
        return messageBiz.findFriendsLastestMessage(userId);
    }

    /**
     * 查询当前用户和各个好友交流的最新一条记录
     * @return
     */
    @GetMapping("getCurrUserFriendsLastestMessage")
    public Map<String, Object> getCurrUserFriendsLastestMessage() {
        String userId = getUserIdOfSecurity();
        if ("".equals(userId)) {
            return messageBiz.getUnauthorizedErrorVo();
        }
        return messageBiz.findFriendsLastestMessage(userId);
    }

}
