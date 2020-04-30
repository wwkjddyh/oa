package com.oa.platform.service;

import com.oa.platform.entity.FileDTO;
import com.oa.platform.entity.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息服务接口
 * 
 * @author Leo
 * @date 2018/5/16
 */
@Service
public interface ChatMessageService extends BaseService<MessageDTO, Long> {

    /**
     * 得到消息列表
     * @param channelId
     * @param maxCreateAt
     * @param limit
     * @return
     */
    List<MessageDTO> listMessage(String channelId, long maxCreateAt, int limit);
    
    /**
     * 添加消息
     * @param dto
     * @return
     */
    MessageDTO saveMessage(MessageDTO dto);
    
    /**
     * 批量添加消息
     * @param dtos
     * @return
     */
    int saveMessage(List<MessageDTO> dtos);
    
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
    String saveFile(FileDTO dto);
    
}
