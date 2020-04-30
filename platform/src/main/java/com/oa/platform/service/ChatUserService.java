package com.oa.platform.service;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.ChatUser;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 用户服务接口
 * 
 * @author Leo
 * @date 2018/4/9
 */
@Service
public interface ChatUserService extends BaseService<ChatUser, String> {

    /**
     * 验证用户登录
     * 
     * @param loginName
     * @param password
     * @return
     */
    ChatUser verifyLogin(String loginName, String password);

    /**
     * 添加用户
     * 
     * @param dto
     * @return
     */

    String saveUser(ChatUser dto);

    /**
     * 根据名称或昵称分页查询用户
     * 
     * @param name
     * @param limit
     * @param offset
     * @return
     */
    PageInfo<ChatUser> listByNameOrNickname(String name, int limit, int offset);
    
    /**
     * 更新用户
     * @param dto
     * @param updateNullValueField
     * @return
     */

    ChatUser updateUser(ChatUser dto, boolean updateNullValueField);
    
    /**
     * 
     * @param channelId
     * @param username
     * @param limit
     * @param offset
     * @return
     */
    PageInfo<ChatUser> listNonMembers(String channelId, String username, int limit, int offset);
    
    /**
     * 批量下线用户
     * @param userIds
     * @return
     */

    int batchOffline(Set<String> userIds);
    
    /**
     * 修改用户口令
     * @param userId
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return
     */

    int updatePassword(String userId, String username, String oldPassword, String newPassword);

}
