package com.oa.platform.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.ChatUser;
import com.oa.platform.service.ChatUserService;

/**
 * 聊天：用户业务处理
 * @author
 * @create 2020/04/30
 */
@Component
public class ChatUserBiz extends BaseBiz {
	
	@Autowired
	private ChatUserService chatUserService;
	
	/**
	 * 根据名称或昵称分页查询用户
	 * @param name
	 * @param limit
	 * @param offset
	 * @return
	 */
	public PageInfo<ChatUser> listByNameOrNickname(String name, int limit, int offset) {
		return chatUserService.listByNameOrNickname(name, limit, offset);
	}
}
