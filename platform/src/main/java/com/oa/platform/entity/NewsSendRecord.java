package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 消息发送记录
 * @author jianbo.feng
 * @date 2020/03/13
 */
public class NewsSendRecord implements Serializable {

    private static final long serialVersionUID = 8629268802315526354L;

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

    /**
     * 发送者邮箱(邮件发送时)
     */
    private String senderMail;

    /**
     * 接收者邮箱(邮件发送时)
     */
    private String receiverMail;

    /**
     * 发送者手机号码(发送短信时)
     */
    private String senderMobileNumber;

    /**
     * 接收者手机号码(发送短信时)
     */
    private String receiverMobileNumber;

    /**
     * 发送者备注(其他类型消息发送时)
     */
    private String senderRemark;

    /**
     * 接收者备注(其他类型消息发送时)
     */
    private String receiverRemark;

    /**
     * 消息内容
     */
    private String newsContent;

    /**
     * 消息标签
     */
    private String newsTags;

    /**
     * 消息备注
     */
    private String newsRemark;

    /**
     * 消息对象
     */
    private News news;

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

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getReceiverMail() {
        return receiverMail;
    }

    public void setReceiverMail(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getSenderMobileNumber() {
        return senderMobileNumber;
    }

    public void setSenderMobileNumber(String senderMobileNumber) {
        this.senderMobileNumber = senderMobileNumber;
    }

    public String getReceiverMobileNumber() {
        return receiverMobileNumber;
    }

    public void setReceiverMobileNumber(String receiverMobileNumber) {
        this.receiverMobileNumber = receiverMobileNumber;
    }

    public String getSenderRemark() {
        return senderRemark;
    }

    public void setSenderRemark(String senderRemark) {
        this.senderRemark = senderRemark;
    }

    public String getReceiverRemark() {
        return receiverRemark;
    }

    public void setReceiverRemark(String receiverRemark) {
        this.receiverRemark = receiverRemark;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsTags() {
        return newsTags;
    }

    public void setNewsTags(String newsTags) {
        this.newsTags = newsTags;
    }

    public String getNewsRemark() {
        return newsRemark;
    }

    public void setNewsRemark(String newsRemark) {
        this.newsRemark = newsRemark;
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
                ", newsTitle='" + newsTitle + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", senderMail='" + senderMail + '\'' +
                ", receiverMail='" + receiverMail + '\'' +
                ", senderMobileNumber='" + senderMobileNumber + '\'' +
                ", receiverMobileNumber='" + receiverMobileNumber + '\'' +
                ", senderRemark='" + senderRemark + '\'' +
                ", receiverRemark='" + receiverRemark + '\'' +
                '}';
    }
}
