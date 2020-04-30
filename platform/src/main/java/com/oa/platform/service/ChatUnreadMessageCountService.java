package com.oa.platform.service;


import org.springframework.stereotype.Service;

/**
 * 未读消息数量服务接口
 * @author Administrator
 *
 */
@Service
public interface ChatUnreadMessageCountService {
    
    /**
     * 批量添加未读消息数量
     * @param userIds
     * @param channelId
     * @param total
     * @return
     */

    int batchSaveUnreadMessageCount(String[] userIds, String channelId, short total);
    
    /**
     * 更新未读消息数量
     * @param userId
     * @param channelId
     * @param total
     * @return
     */

    int updateUnreadMessageCount(String userId, String channelId, short total);
    
    /**
     * 批量更新未读消息数量
     * @param userIds
     * @param channelId
     * @param total
     * @return
     */
    int batchUpdateUnreadMessageCount(String[] userIds, String channelId, short total);
    
    /**
     * 批量增加未读消息数量
     * @param userIds
     * @param channelId
     * @param quantity
     * @return
     */

    int batchIncreaseUnreadMessageCount(String[] userIds, String channelId, short quantity);
    
    /**
     * 增加未读消息数量
     * @param userId
     * @param channelId
     * @param quantity
     * @return
     */

    int increaseUnreadMessageCount(String userId, String channelId, short quantity);

}
