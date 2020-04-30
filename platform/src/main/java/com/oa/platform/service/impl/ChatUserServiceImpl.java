package com.oa.platform.service.impl;


import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.UserDTO;
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
public class ChatUserServiceImpl extends AbstractBaseService<UserDTO, String> implements ChatUserService {

    @Autowired
    private ChatUserRepository chatUserRepository;

    public ChatUserServiceImpl() {
        super(UserDTO.class);
    }

    @Override
    public UserDTO verifyLogin(String loginName, String password) {
        return null;
    }

    @Override
    public String saveUser(UserDTO dto) {
        return null;
    }

    @Override
    public PageInfo<UserDTO> listByNameOrNickname(String name, int limit, int offset) {
        return null;
    }

    @Override
    public UserDTO updateUser(UserDTO dto, boolean updateNullValueField) {
        return null;
    }

    @Override
    public PageInfo<UserDTO> listNonMembers(String channelId, String username, int limit, int offset) {
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
