package com.oa.platform.biz;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Sets;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Area;
import com.oa.platform.entity.Message;
import com.oa.platform.entity.MessageRoom;
import com.oa.platform.entity.UserMessageStat;
import com.oa.platform.service.MessageService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 消息
 * @author jianbo
 * @create 2020/04/16
 */
@Component
public class MessageBiz extends BaseBiz {

    @Autowired
    private MessageService messageService;

    /**
     * 是否房间信息重复
     * @param roomId 房间唯一标识
     * @param categoryId 类型ID
     * @param roomTitle 房间标题
     * @return
     */
    boolean validRepeatOfRoom(String roomId, String categoryId, String roomTitle) {
        boolean isRepeat = false;
        roomId = StringUtil.trim(roomId);
        categoryId = StringUtil.trim(categoryId);
        roomTitle = StringUtil.trim(roomTitle);
        MessageRoom room = new MessageRoom();
        room.setCategoryId(categoryId);
        room.setRoomTitle(roomTitle);
        room.setRecordFlag(Constants.INT_NORMAL);
        List<MessageRoom> rooms = messageService.findMessageRoom(room);
        if(rooms != null && !rooms.isEmpty()) {
            if("".equals(roomId)) {
                isRepeat = true;
            }
            else {
                final String finalRoomId = roomId;
                isRepeat = rooms.parallelStream().anyMatch(e -> !e.getRoomId().equals(finalRoomId));
            }
        }
        return isRepeat;
    }

    /**
     * 保存消息
     * @param categoryId 分类ID
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @param content 消息内容
     * @return
     */
    public Map<String, Object> saveMessage(String categoryId, String senderId, String receiverId,
                                           String content) {
        try {
            categoryId = StringUtil.trim(categoryId);
            senderId = StringUtil.trim(senderId);
            receiverId = StringUtil.trim(receiverId);
            content = StringUtil.trim(content);
            if ("".equals(categoryId) || "".equals(senderId) || "".equals(receiverId) || "".equals(content)) {
                ret = this.getParamErrorVo();
            }
            else {
                Message message = new Message();
                message.setCategoryId(categoryId);
                message.setSenderId(senderId);
                message.setReceiverId(receiverId);
                message.setContent(content);
                message.setRecordId(StringUtil.getRandomUUID());
                message.setRecordFlag(Constants.INT_NORMAL);
                messageService.save(message);
                // 添加或更新用户统计信息
                Set<String> userIds = Sets.newHashSet(senderId, receiverId);
                messageService.saveOrUpdateUserMessageStatByUserIds(userIds);

                ret = this.getSuccessVo("", "");
            }
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 保存消息
     * @param messages 消息列表
     * @return
     */
    public Map<String, Object> saveMessage(List<Message> messages) {
        if (messages == null || messages.isEmpty()) {
            ret = this.getParamErrorVo();
        }
        else {
            if (messages.size() == 1) {
                Message message = messages.get(0);
                ret = saveMessage(message.getCategoryId(), message.getSenderId(), message.getReceiverId(), message.getContent());
            }
            else {
                try {
                    messageService.batchSave(messages);
                    // 添加或更新用户统计信息
                    Set<String> userIds = Sets.newHashSet();
                    for (Message message : messages) {
                        String senderId = StringUtil.trim(message.getSenderId());
                        String receiverId = StringUtil.trim(message.getReceiverId());
                        if (!"".equals(senderId)) {
                            userIds.add(senderId);
                        }
                        if (!"".equals(receiverId)) {
                            userIds.add(receiverId);
                        }
                    }
                    messageService.saveOrUpdateUserMessageStatByUserIds(userIds);
                    ret = this.getSuccessVo("", "");
                }
                catch (Exception e) {
                    loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                    ret = this.getErrorVo();
                }
            }
        }
        return ret;
    }

    /**
     * 保存或修改房间(/群)信息
     * @param roomId 房间唯一标识
     * @param categoryId 分类ID
     * @param roomTitle 房间名称
     * @param roomIntro 房间简介
     * @param roomBulletin 房间公告
     * @param recordFlag 信息标识（0, 删除 ；1，正常）
     * @return
     */
    public Map<String, Object> saveRoom(String roomId, String categoryId, String roomTitle, String roomIntro,
                                        String roomBulletin, Integer recordFlag) {
        roomId = StringUtil.trim(roomId);
        categoryId = StringUtil.trim(categoryId);
        roomTitle = StringUtil.trim(roomTitle);
        roomIntro = StringUtil.trim(roomIntro);
        roomBulletin = StringUtil.trim(roomBulletin);
        try {
//            boolean isRepeat = this.validRepeatOfRoom(roomId, categoryId, roomTitle);

//            boolean isEdit = false;
            if (!"".equals(roomId)) {
//                isEdit = true;

                MessageRoom room = messageService.findMessageRoomById(roomId);
                if (room == null) {
                    ret = this.getParamErrorVo();
                }
                else {
                    room = new MessageRoom();
                    room.setRoomId(roomId);
                    room.setCategoryId(categoryId);
                    room.setRoomTitle(roomTitle);
                    room.setRoomIntro(roomIntro);
                    room.setRoomBulletin(roomBulletin);
                    room.setRecordFlag(recordFlag);
                    room.setUpdaterId(this.getUserIdOfSecurity());
                    room.setUpdaterName(DateUtil.currDateFormat(null));
                    messageService.updateMessageRoom(room);
                    ret = this.getSuccessVo("", "");
                }
            }
            else {
                MessageRoom room = new MessageRoom();
                room.setRoomId(StringUtil.getRandomUUID());
                room.setCategoryId(categoryId);
                room.setRoomTitle(roomTitle);
                room.setRoomIntro(roomIntro);
                room.setRoomBulletin(roomBulletin);
                room.setRecordFlag(Constants.INT_NORMAL);
                room.setRecorderId(this.getUserIdOfSecurity());
                messageService.saveMessageRoom(room);
                ret = this.getSuccessVo("", "");
            }
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 根据ID删除消息
     * @param recordId 消息ID
     * @return
     */
    public Map<String, Object> deleteMessageById(String recordId) {
        recordId = StringUtil.trim(recordId);
        if ("".equals(recordId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Message message = messageService.getById(recordId);
                if (message == null) {
                    ret = this.getParamErrorVo();
                }
                else {
                    message.setRecordFlag(Constants.INT_DEL);
                    messageService.update(message);
                    // 添加或更新用户统计信息
                    Set<String> userIds = Sets.newHashSet(message.getReceiverId(), message.getSenderId());
                    messageService.saveOrUpdateUserMessageStatByUserIds(userIds);
                    ret = this.getSuccessVo("", "");
                }
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据ID删除房间信息
     * @param roomId 房间ID
     * @return
     */
    public Map<String, Object> deleteRoomById(String roomId) {
        roomId = StringUtil.trim(roomId);
        if ("".equals(roomId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                MessageRoom room = new MessageRoom();
                room.setRoomId(roomId);
                room.setRecordFlag(Constants.INT_DEL);
                messageService.updateMessageRoom(room);
                ret = this.getSuccessVo("", "");
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }
}
