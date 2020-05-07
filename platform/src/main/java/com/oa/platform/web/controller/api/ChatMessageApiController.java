package com.oa.platform.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oa.platform.biz.ChatMessageBiz;
import com.oa.platform.entity.ChatMessage;
import com.oa.platform.web.controller.BaseController;

/**
 * 即时聊天：消息接口
 * @author Feng
 * @date 2020/04/30
 */
@RestController
@RequestMapping("/api/chat/messages")
public class ChatMessageApiController extends BaseController {

    @Autowired
    private ChatMessageBiz chatMessageBiz;
    
    /**
     * 获取聊天内容
     * @param channelId
     * @param maxCreateAt
     * @param limit
     * @return
     */
    @GetMapping("listMessage")
    public ResponseEntity<List<ChatMessage>> listMessage(@RequestParam("channelId") String channelId,
            @RequestParam("maxCreateAt") long maxCreateAt, @RequestParam("limit") int limit) {
    	List<ChatMessage> result = chatMessageBiz.listMessage(channelId, maxCreateAt, limit);
    	return ResponseEntity.ok(result);
    }
//    /**
//     * 发送消息
//     * @param request
//     * @param token
//     * @param body
//     * @return
//     */
//    @PostMapping("saveMessage")
//    public ResponseEntity<?> saveMessage(FullHttpRequest request, @RequestHeader("X-Token") String token,
//            @RequestBody String body) {
//    	return ResponseEntity.notFound().build();
//    }
}
