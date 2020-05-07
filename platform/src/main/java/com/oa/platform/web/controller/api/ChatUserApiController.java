package com.oa.platform.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.oa.platform.biz.ChatUserBiz;
import com.oa.platform.entity.ChatUser;
import com.oa.platform.web.controller.BaseController;

/**
 * 即时聊天：用户接口
 * @author Feng
 * @date 2020/04/30
 */
@RestController
@RequestMapping("/api/chat/user")
public class ChatUserApiController extends BaseController {

    @Autowired
    private ChatUserBiz chatUserBiz;
    
//    /**
//     * 获取用户列表
//     * @param name
//     * @param limit
//     * @param offset
//     * @return
//     */
//    @GetMapping("listUser")
//    public ResponseEntity<Page<ChatUser>> listUser(@RequestParam("name") String name, @RequestParam("limit") int limit,
//            @RequestParam("offset") int offset) {
//    	Page<ChatUser> result = chatUserBiz.listByNameOrNickname(name, limit, offset);
//    	return ResponseEntity.ok(result);
//    }

}
