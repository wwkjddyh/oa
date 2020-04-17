package com.oa.platform.service.impl;

import com.beust.jcommander.internal.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.Message;
import com.oa.platform.entity.MessageRoom;
import com.oa.platform.entity.UserMessageStat;
import com.oa.platform.repository.MessageRepository;
import com.oa.platform.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
