package com.oa.platform.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户-消息关联信息
 * @author jianbo.feng
 * @create 2020/04/20
 */
public class UserMessage implements Serializable {

    /**
     * 唯一标识
     */
    private String recordId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息状态(0, 已发送成功; 1: 未发送成功; 10, 已接收成功; 11, 未接收成功;)
     */
    private Integer status;

    private List<String> recordIds;

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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(List<String> recordIds) {
        this.recordIds = recordIds;
    }

    public UserMessage() {
    }

    public UserMessage(String recordId, String userId, String messageId, Integer status) {
        this.recordId = recordId;
        this.userId = userId;
        this.messageId = messageId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "recordId='" + recordId + '\'' +
                ", userId='" + userId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", status=" + status +
                '}';
    }
}
