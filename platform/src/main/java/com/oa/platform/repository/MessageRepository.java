package com.oa.platform.repository;

import com.oa.platform.entity.Message;
import com.oa.platform.entity.MessageRoom;
import com.oa.platform.entity.UserMessage;
import com.oa.platform.entity.UserMessageStat;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息数据处理
 * @author jianbo.feng
 * @create 2020/04/15
 */
@Repository
public interface MessageRepository extends BaseRepository<Message, String> {

    void insertMessageRoom(MessageRoom messageRoom);

    void insertUserMessageStat(UserMessageStat userMessageStat);

    void updateMessageRoom(MessageRoom messageRoom);

    void updateUserMessageStat(UserMessageStat userMessageStat);

    void deleteMessageRoom(MessageRoom messageRoom);

    void deleteUserMessageStat(UserMessageStat userMessageStat);

    MessageRoom findMessageRoomById(String roomId);

    UserMessageStat findUserMessageStatById(String recordId);

    List<MessageRoom> findMessageRoom(MessageRoom messageRoom);

    List<UserMessageStat> findUserMessageStat(UserMessageStat userMessageStat);

    /**
     * 批量更新用户消息统计信息
     * @param userMessageStats 用户消息统计信息列表
     */
    void updateBatchUserMessageStat(List<UserMessageStat> userMessageStats);

    /**
     * 批量插入用户消息统计信息
     * @param userMessageStats 用户消息统计信息列表
     */
    void batchInsertUserMessageStat(List<UserMessageStat> userMessageStats);

    /**
     * 批量插入消息
     * @param messages 消息列表
     */
    void batchInsert(List<Message> messages);

    /**
     * 根据用户ID（统计查询消息后）插入用户消息统计信息
     * @param userId 用户ID
     */
    void insertUserMessageStatByUserId(String userId);

    /**
     * 根据用户ID（统计查询消息后）插入用户消息统计信息
     * @param userIds 用户ID列表
     */
    void insertUserMessageStatByUserIds(List<String> userIds);

    /**
     * 插入用户-消息关联信息
     * @param userMessage 用户-消息关联信息
     */
    void insertUserMessage(UserMessage userMessage);

    /**
     * 批量插入用户-消息关联信息列表
     * @param userMessages 用户-消息关联信息列表
     */
    void batchInsertUserMessage(List<UserMessage> userMessages);

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
}
