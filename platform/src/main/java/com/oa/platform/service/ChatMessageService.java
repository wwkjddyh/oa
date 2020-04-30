package com.oa.platform.service;

import com.oa.platform.entity.ChatFile;
import com.oa.platform.entity.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息服务接口
 * 
 * @author Leo
 * @date 2018/5/16
 */
@Service
public interface ChatMessageService extends BaseService<ChatMessage, Long> {

    /**
     * 得到消息列表
     * @param channelId
     * @param maxCreateAt
     * @param limit
     * @return
     */
    List<ChatMessage> listMessage(String channelId, long maxCreateAt, int limit);
    
    /**
     * 添加消息
     * @param dto
     * @return
     */
    ChatMessage saveMessage(ChatMessage dto);
    
    /**
     * 批量添加消息
     * @param dtos
     * @return
     */
    int saveMessage(List<ChatMessage> dtos);
    
    /**
     * 读取消息
     * @param channelId
     * @param userId
     * @param total
     * @return
     */
    int readMessage(String channelId, String userId, short total);
    
    /**
     * 删除消息
     * @param messageId
     * @param senderId
     * @param channelId
     * @param toUserId
     * @return
     */
    int removeMessage(Long messageId, String senderId, String channelId, String toUserId);
    
    /**
     * 添加文件
     * @param dto
     * @return
     */
    String saveFile(ChatFile dto);
    
}
