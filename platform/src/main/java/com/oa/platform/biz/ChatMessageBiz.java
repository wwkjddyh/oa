package com.oa.platform.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oa.platform.entity.ChatMessage;
import com.oa.platform.service.ChatMessageService;

/**
 * 聊天：消息业务处理
 * @author
 * @create 2020/04/30
 */
@Component
public class ChatMessageBiz extends BaseBiz {
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	
	/**
	 * 获取聊天内容
	 * @param channelId
	 * @param maxCreateAt
	 * @param limit
	 * @return
	 */
	public List<ChatMessage> listMessage(String channelId, long maxCreateAt, int limit) {
		return chatMessageService.listMessage(channelId, maxCreateAt, limit);
	}
}
