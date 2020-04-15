package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 用户消息统计
 * @author jianbo.feng
 * @create 2020/04/15
 */
public class UserMessageStat implements Serializable {

    /**
     * 唯一标识
     */
    private String recordId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 最新一条聊天记录ID
     */
    private String lastestMessgeId;

    /**
     * 用户消息统计(默认0l)
     */
    private Long messageTotal;

    /**
     * (最新)消息发送者ID
     */
    private String senderId;

    /**
     * (最新)消息接收者ID
     */
    private String receiverId;

    /**
     * (最新)消息内容
     */
    private String content;

    /**
     * (最新消息)录入时间
     */
    private String recordTime;

    /**
     * (最新消息所属)房间ID(如果为空，则为点对点信息)
     */
    private String roomId;


    /**
     * (最新消息)分类名称
     */
    private String categoryName;

    /**
     * (最新消息)发送者姓名
     */
    private String senderName;

    /**
     * (最新消息)接收者姓名
     */
    private String receiverName;

    /**
     * (最新消息)房间名称
     */
    private String roomTitle;

    /**
     * 关键字
     */
    private String key;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastestMessgeId() {
        return lastestMessgeId;
    }

    public void setLastestMessgeId(String lastestMessgeId) {
        this.lastestMessgeId = lastestMessgeId;
    }

    public Long getMessageTotal() {
        return messageTotal;
    }

    public void setMessageTotal(Long messageTotal) {
        this.messageTotal = messageTotal;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UserMessageStat() {
    }

    public UserMessageStat(String recordId, String userId, String lastestMessgeId, Long messageTotal) {
        this.recordId = recordId;
        this.userId = userId;
        this.lastestMessgeId = lastestMessgeId;
        this.messageTotal = messageTotal;
    }

    @Override
    public String toString() {
        return "UserMessageStat{" +
                "recordId='" + recordId + '\'' +
                ", userId='" + userId + '\'' +
                ", lastestMessgeId='" + lastestMessgeId + '\'' +
                ", messageTotal=" + messageTotal +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", roomId='" + roomId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", roomTitle='" + roomTitle + '\'' +
                '}';
    }
}
