package com.oa.platform.service;

import com.oa.platform.entity.ChatUserChannel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户频道服务接口
 * 
 * @author Leo
 * @date 2018/4/20
 */
@Service
public interface ChatUserChannelService extends BaseService<ChatUserChannel, String> {
    
    /**
     * 得到用户频道列表
     * @param userId
     * @param type
     * @param limit
     * @return
     */
    List<ChatUserChannel> listUserChannel(String userId, String type, int limit);
    
    /**
     * 得到用户频道
     * @param userId
     * @param channelId
     * @return
     */
    ChatUserChannel get(String userId, String channelId);
    
    /**
     * 更新用户频道
     * @param channelId
     * @param userId
     * @param displayName
     * @return
     */

    int updateDisplayName(String channelId, String userId, String displayName);
    
    /**
     * 隐藏频道
     * @param userId
     * @param channelId
     * @return
     */

    int hideChannel(String userId, String channelId);
    
    /**
     * 根据名称得到用户频道列表
     * @param userId
     * @param name
     * @param type
     * @return
     */
    List<ChatUserChannel> listByName(String userId, String name, String type);

}
