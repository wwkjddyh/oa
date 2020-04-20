package com.oa.platform.service;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.Message;
import com.oa.platform.entity.MessageRoom;
import com.oa.platform.entity.UserMessage;
import com.oa.platform.entity.UserMessageStat;

import java.util.List;
import java.util.Set;

/**
 * 消息
 * @author jianbo.feng
 * @create 2020/04/15
 */
public interface MessageService extends BaseService<Message, String> {

    void saveMessageRoom(MessageRoom messageRoom);

    void saveUserMessageStat(UserMessageStat userMessageStat);

    void updateMessageRoom(MessageRoom messageRoom);

    void updateUserMessageStat(UserMessageStat userMessageStat);

    void deleteMessageRoom(MessageRoom messageRoom);

    void deleteUserMessageStat(UserMessageStat userMessageStat);

    MessageRoom findMessageRoomById(String roomId);

    UserMessageStat findUserMessageStatById(String recordId);

    List<MessageRoom> findMessageRoom(MessageRoom messageRoom);

    List<UserMessageStat> findUserMessageStat(UserMessageStat userMessageStat);

    PageInfo<MessageRoom> searchMessageRoom(MessageRoom messageRoom, int pageNum, int pageSize);

    PageInfo<UserMessageStat> searchUserMessageStat(UserMessageStat userMessageStat, int pageNum, int pageSize);

    /**
     * 批量更新用户消息统计信息
     * @param userMessageStats 用户消息统计信息列表
     */
    void updateBatchUserMessageStat(List<UserMessageStat> userMessageStats);

    /**
     * 批量保存用户消息统计信息
     * @param userMessageStats 用户消息统计信息列表
     */
    void batchSaveUserMessageStat(List<UserMessageStat> userMessageStats);

    /**
     * 批量保存消息
     * @param messages 消息列表
     */
    void batchSave(List<Message> messages);

    /**
     * 根据用户ID（统计查询消息后）保存用户消息统计信息
     * @param userId 用户ID
     */
    void saveUserMessageStatByUserId(String userId);

    /**
     * 根据用户ID组（统计查询消息后）保存用户消息统计信息
     * @param userIds 用户ID列表
     */
    void saveUserMessageStatByUserIds(List<String> userIds);

    /**
     * 根据用户ID组（统计查询消息后）保存用户消息统计信息
     * @param userIds 用户ID列表
     */
    void saveOrUpdateUserMessageStatByUserIds(Set<String> userIds);

    /**
     * 保存用户-消息关联信息
     * @param userMessage 用户-消息关联信息
     */
    void saveUserMessage(UserMessage userMessage);

    /**
     * 批量保存用户-消息关联信息列表
     * @param userMessages 用户-消息关联信息列表
     */
    void batchSaveUserMessage(List<UserMessage> userMessages);

    /**
     * 修改用户-消息关联信息
     * @param userMessage 用户-消息关联信息
     */
    void updateUserMessage(UserMessage userMessage);

    /**
     * 批量修改用户-消息关联信息
     * @param userMessages 用户-消息关联信息列表
     */
    void updateBatchUserMessage(List<UserMessage> userMessages);

    /**
     * 删除用户-消息关联信息
     * @param userMessage 用户-消息关联信息
     */
    void deleteUserMessage(UserMessage userMessage);

    /**
     * 查询用户消息(必须设置属性associatedUserId)
     * @param message 参数
     * @return
     */
    List<Message> findUserMessage(Message message);

    /**
     * 分页查询用户消息(必须设置属性associatedUserId)
     * @param message 参数
     * @return
     */
    PageInfo<Message> searchUserMessage(Message message, int pageNum, int pageSize);


}
