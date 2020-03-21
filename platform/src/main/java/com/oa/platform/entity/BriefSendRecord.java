package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 简报发送记录
 * @author jianbo.feng
 * @date 2020/03/21
 */
public class BriefSendRecord implements Serializable {

    /**
     * 唯一标识
     */
    private String recordId;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 信息标识(1,正常;0,删除;)
     */
    private Integer recordFlag;

    /**
     * 查看时间
     */
    private String viewTime;

    /**
     * 简报ID
     */
    private String briefId;

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 接收者ID
     */
    private String receiverId;

    /**
     * 状态(0, 未查看; 1, 已查看)
     */
    private Integer status;

    /**
     * 发送者备注
     */
    private String senderRemark;

    /**
     * 接收者备注
     */
    private String receiverRemark;

    /**
     * 搜索关键字(查询用)
     */
    private String key;

    /**
     * 简报标题(查询用)
     */
    private String briefTitle;

    /**
     * 发送者姓名(查询用)
     */
    private String senderName;

    /**
     * 接收者姓名(查询用)
     */
    private String receiverName;

    /**
     * 文档信息
     */
    private Article article;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Integer recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getViewTime() {
        return viewTime;
    }

    public void setViewTime(String viewTime) {
        this.viewTime = viewTime;
    }

    public String getBriefId() {
        return briefId;
    }

    public void setBriefId(String briefId) {
        this.briefId = briefId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBriefTitle() {
        return briefTitle;
    }

    public void setBriefTitle(String briefTitle) {
        this.briefTitle = briefTitle;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public BriefSendRecord() {
    }

    @Override
    public String toString() {
        return "BriefSendRecord{" +
                "recordId='" + recordId + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", recordFlag=" + recordFlag +
                ", viewTime='" + viewTime + '\'' +
                ", briefId='" + briefId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", status=" + status +
                ", senderRemark='" + senderRemark + '\'' +
                ", receiverRemark='" + receiverRemark + '\'' +
                ", briefTitle='" + briefTitle + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                '}';
    }
}
