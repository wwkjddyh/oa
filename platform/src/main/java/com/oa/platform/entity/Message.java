package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 消息
 * @author jianbo.feng
 * @create 2020/04/15
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -8349174778691884518L;

    /**
     * 唯一标识
     */
    private String recordId;

    /**
     * 消息类型ID
     */
    private String categoryId;

    /**
     * 消息发送者ID
     */
    private String senderId;

    /**
     * 消息接收者ID
     */
    private String receiverId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 录入时间
     */
    private String recordTime;

    /**
     * 信息存储状态(0,删除;1,正常)
     */
    private Integer recordFlag;

    /**
     * 房间ID(如果为空，则为点对点信息)
     */
    private String roomId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 发送者姓名
     */
    private String senderName;

    /**
     * 接收者姓名
     */
    private String receiverName;

    /**
     * 房间名称
     */
    private String roomTitle;

    /**
     * 消息状态(0, 已发送成功; 1: 未发送成功; 10, 已接收成功; 11, 未接收成功;)-->关联查询获取
     */
    private Integer status;

    /**
     * 关联用户ID(关联查询用)
     */
    private String associatedUserId;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public Integer getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Integer recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getAssociatedUserId() {
        return associatedUserId;
    }

    public void setAssociatedUserId(String associatedUserId) {
        this.associatedUserId = associatedUserId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Message() {
    }

    public Message(String recordId, String categoryId, String senderId, String receiverId,
                   String content, String recordTime, Integer recordFlag, String roomId, Integer status) {
        this.recordId = recordId;
        this.categoryId = categoryId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.recordTime = recordTime;
        this.recordFlag = recordFlag;
        this.roomId = roomId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "recordId='" + recordId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", recordFlag=" + recordFlag +
                ", roomId='" + roomId + '\'' +
                ", status=" + status +
                ", categoryName='" + categoryName + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", roomTitle='" + roomTitle + '\'' +
                '}';
    }
}
