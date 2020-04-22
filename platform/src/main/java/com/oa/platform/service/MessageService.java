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

    /**
     * 查询用户和各个好友交流的最新一条记录
     * @param userId 用户ID
     * @return
     */
    List<Message> findFriendsLastestMessage(String userId);

    /**
     * 解析消息内容
     * @param message 消息
     */
    void parsingMessageContent(Message message);

    /**
     * 解析消息内容
     * @param messages 消息列表
     */
    void parsingMessageContent(List<Message> messages);

    /**
     * 解析消息内容
     * @param pageInfo 分页信息
     */
    void parsingMessageContent(PageInfo<Message> pageInfo);

    /**
     * 查询用户与(某个)朋友之间的聊天记录(倒序排列)
     * @param userId 用户ID
     * @param friendId 好友ID
     * @return
     */
    List<Message> getUserChatHistoryWithFriend(String userId, String friendId);

    /**
     * 分页查询用户与(某个)朋友之间的聊天记录(倒序排列)
     * @param userId 用户ID
     * @param friendId 好友ID
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    PageInfo<Message> getUserChatHistoryWithFriend(String userId, String friendId, int pageNum, int pageSize);
}
