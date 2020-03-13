package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 消息发送记录
 * @author jianbo.feng
 * @date 2020/03/13
 */
public class NewsSendRecord implements Serializable {

    /**
     * 记录ID
     */
    private String recordId;

    /**
     * 消息记录ID
     */
    private String newsId;

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 接收者ID
     */
    private String receiverId;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 查看时间
     */
    private String viewTime;

    /**
     * 状态(0, 未查看; 1, 已查看)
     */
    private Integer status;

    /**
     * 信息标识(1,正常;0,删除;)
     */
    private Integer recordFlag;

    /**
     * 搜索关键字(查询用)
     */
    private String key;

    /**
     * 消息标题(查询用)
     */
    private String newsTitle;

    /**
     * 发送者姓名(查询用)
     */
    private String senderName;

    /**
     * 接收者姓名(查询用)
     */
    private String receiverName;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
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

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Integer recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
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

    public String getViewTime() {
        return viewTime;
    }

    public void setViewTime(String viewTime) {
        this.viewTime = viewTime;
    }

    public NewsSendRecord() {
    }

    @Override
    public String toString() {
        return "NewsSendRecord{" +
                "recordId='" + recordId + '\'' +
                ", newsId='" + newsId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", viewTime='" + viewTime + '\'' +
                ", status=" + status +
                ", recordFlag=" + recordFlag +
                ", key='" + key + '\'' +
                ", newsTitle='" + newsTitle + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                '}';
    }
}
