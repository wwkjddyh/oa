package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 登录日志
 * @author Feng
 * @date 2019/03/01
 */
public class LoginLog implements Serializable {

    /**
     * 主键，唯一标识
     */
    private String logId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * IP地址
     */
    private String ipAddr;

    /**
     * 信息录入时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String recordTime;

    /**
     * 地址ID(默认为空或空字符串)
     */
    private String spaceId;

    /**
     * 地址名称
     */
    private String spaceName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 查询关键字
     */
    private String key;

    public LoginLog() {
    }

    public LoginLog(String logId, String userId, String ipAddr, String recordTime) {
        this.logId = logId;
        this.userId = userId;
        this.ipAddr = ipAddr;
        this.recordTime = recordTime;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LoginLog(String logId, String userId, String ipAddr, String recordTime, String spaceId) {
        this.logId = logId;
        this.userId = userId;
        this.ipAddr = ipAddr;
        this.recordTime = recordTime;
        this.spaceId = spaceId;
    }

    @Override
    public String toString() {
        return "LoginLog{" +
                "logId='" + logId + '\'' +
                ", userId='" + userId + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", spaceId='" + spaceId + '\'' +
                '}';
    }
}
