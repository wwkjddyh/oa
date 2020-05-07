package com.oa.platform.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.ChatUser;
import com.oa.platform.service.ChatUserService;
import com.oa.platform.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 用户服务实现类
 * 
 * @author Leo
 * @date 2018/4/9
 */
public class ChatUserServiceImpl extends AbstractBaseService<ChatUser, String> implements ChatUserService {

    @Autowired
    private ChatUserRepository chatUserRepository;

    public ChatUserServiceImpl() {
        super(ChatUser.class);
    }

    @Override
    public ChatUser verifyLogin(String loginName, String password) {
        return null;
    }

    @Override
    public String saveUser(ChatUser dto) {
        return null;
    }

    @Override
    public PageInfo<ChatUser> listByNameOrNickname(String name, int limit, int offset) {
    	PageHelper.startPage(offset, limit);
    	return new PageInfo(chatUserRepository.listByNameOrNickname(name));
    }

    @Override
    public ChatUser updateUser(ChatUser dto, boolean updateNullValueField) {
        return null;
    }

    @Override
    public PageInfo<ChatUser> listNonMembers(String channelId, String username, int limit, int offset) {
        return null;
    }

    @Override
    public int batchOffline(Set<String> userIds) {
        return 0;
    }

    @Override
    public int updatePassword(String userId, String username, String oldPassword, String newPassword) {
        return 0;
    }
}
