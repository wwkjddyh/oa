package com.oa.platform.service.impl;

import com.beust.jcommander.internal.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.Message;
import com.oa.platform.entity.MessageRoom;
import com.oa.platform.entity.UserMessage;
import com.oa.platform.entity.UserMessageStat;
import com.oa.platform.repository.MessageRepository;
import com.oa.platform.service.MessageService;
import com.oa.platform.util.StringUtil;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MessageServiceImpl extends AbstractBaseService<Message, String> implements MessageService {
    public MessageServiceImpl() {
        super(Message.class);
    }

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveMessageRoom(MessageRoom messageRoom) {
        messageRepository.insertMessageRoom(messageRoom);
    }

    @Override
    public void saveUserMessageStat(UserMessageStat userMessageStat) {
        messageRepository.insertUserMessageStat(userMessageStat);
    }

    @Override
    public void updateMessageRoom(MessageRoom messageRoom) {
        messageRepository.updateMessageRoom(messageRoom);
    }

    @Override
    public void updateUserMessageStat(UserMessageStat userMessageStat) {
        messageRepository.updateUserMessageStat(userMessageStat);
    }

    @Override
    public void deleteMessageRoom(MessageRoom messageRoom) {
        messageRepository.deleteMessageRoom(messageRoom);
    }

    @Override
    public void deleteUserMessageStat(UserMessageStat userMessageStat) {
        messageRepository.deleteUserMessageStat(userMessageStat);
    }

    @Override
    public MessageRoom findMessageRoomById(String roomId) {
        return messageRepository.findMessageRoomById(roomId);
    }

    @Override
    public UserMessageStat findUserMessageStatById(String recordId) {
        return messageRepository.findUserMessageStatById(recordId);
    }

    @Override
    public List<MessageRoom> findMessageRoom(MessageRoom messageRoom) {
        return messageRepository.findMessageRoom(messageRoom);
    }

    @Override
    public List<UserMessageStat> findUserMessageStat(UserMessageStat userMessageStat) {
        return messageRepository.findUserMessageStat(userMessageStat);
    }

    @Override
    public PageInfo<MessageRoom> searchMessageRoom(MessageRoom messageRoom, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MessageRoom> records = messageRepository.findMessageRoom(messageRoom == null ? new MessageRoom() : messageRoom);
        return new PageInfo<>(records);
    }

    @Override
    public PageInfo<UserMessageStat> searchUserMessageStat(UserMessageStat userMessageStat, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserMessageStat> records = messageRepository.findUserMessageStat(userMessageStat == null ? new UserMessageStat() : userMessageStat);
        return new PageInfo<>(records);
    }

    @Override
    public void updateBatchUserMessageStat(List<UserMessageStat> userMessageStats) {
        messageRepository.updateBatchUserMessageStat(userMessageStats);
    }

    @Override
    public void batchSaveUserMessageStat(List<UserMessageStat> userMessageStats) {
        messageRepository.batchInsertUserMessageStat(userMessageStats);
    }

    @Override
    public void batchSave(List<Message> messages) {
        messageRepository.batchInsert(messages);
    }

    @Override
    public void saveUserMessageStatByUserId(String userId) {
        messageRepository.insertUserMessageStatByUserId(userId);
    }

    @Override
    public void saveUserMessageStatByUserIds(List<String> userIds) {
        messageRepository.insertUserMessageStatByUserIds(userIds);
    }

    @Override
    public void saveOrUpdateUserMessageStatByUserIds(Set<String> userIds) {
        UserMessageStat userMessageStat = new UserMessageStat();
        List<String> ids = Lists.newArrayList(userIds);
        userMessageStat.setUserIds(ids);
        messageRepository.deleteUserMessageStat(userMessageStat);
        messageRepository.insertUserMessageStatByUserIds(ids);
    }

    @Override
    public void saveUserMessage(UserMessage userMessage) {
        messageRepository.insertUserMessage(userMessage);
    }

    @Override
    public void batchSaveUserMessage(List<UserMessage> userMessages) {
        messageRepository.batchInsertUserMessage(userMessages);
    }

    @Override
    public void updateUserMessage(UserMessage userMessage) {
        messageRepository.updateUserMessage(userMessage);
    }

    @Override
    public void updateBatchUserMessage(List<UserMessage> userMessages) {
        messageRepository.updateBatchUserMessage(userMessages);
    }

    @Override
    public void deleteUserMessage(UserMessage userMessage) {
        messageRepository.deleteUserMessage(userMessage);
    }

    @Override
    public List<Message> findUserMessage(Message message) {
        List<Message> messages = Lists.newArrayList();
        if (message != null && message.getAssociatedUserId() != null) {
            messages = messageRepository.findUserMessage(message);
        }
        return messages;
    }

    @Override
    public PageInfo<Message> searchUserMessage(Message message, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> records = messageRepository.findUserMessage(message == null ? new Message() : message);
        return new PageInfo<>(records);
    }

    @Override
    public List<Message> findFriendsLastestMessage(String userId) {
        userId = StringUtil.trim(userId);
        if ("".equals(userId)) {
            return new ArrayList<>(0);
        }
        return messageRepository.findFriendsLastestMessage(userId);
    }

    @Override
    public void parsingMessageContent(Message message) {
        if (message != null) {
            message.setContent(EmojiParser.parseToUnicode(message.getContent()));
        }
    }

    @Override
    public void parsingMessageContent(List<Message> messages) {
        if (messages != null) {
            messages.parallelStream().forEach(m -> parsingMessageContent(m));
        }
    }

    @Override
    public void parsingMessageContent(PageInfo<Message> pageInfo) {
        if (pageInfo != null) {
            List<Message> messages = pageInfo.getList();
            if (messages == null) {
                pageInfo.setList(new ArrayList<>(0));
            }
            else {
                parsingMessageContent(messages);
                pageInfo.setList(messages);
            }
        }
    }

    @Override
    public List<Message> getUserChatHistoryWithFriend(String userId, String friendId) {
        return messageRepository.findUserChatHistoryWithFriend(userId, friendId);
    }

    @Override
    public PageInfo<Message> getUserChatHistoryWithFriend(String userId, String friendId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> records = messageRepository.findUserChatHistoryWithFriend(userId, friendId);
        return new PageInfo<>(records);
    }
}
