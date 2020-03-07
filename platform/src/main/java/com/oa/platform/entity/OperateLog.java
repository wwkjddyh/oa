package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 操作日志
 * @author Feng
 * @date 2019/03/01
 */
public class OperateLog implements Serializable {

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
     * 操作描述
     */
    private String operateDesc;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 信息修改时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String updateTime;

    /**
     * 信息修改者id
     */
    private String updateUserId;

    /**
     * 信息标识(1,正常;0,删除;)
     */
    private Integer recordFlag;

    public OperateLog() {
    }

    public OperateLog(String logId, String userId, String ipAddr, String recordTime, String operateDesc,
                      String operateType, String updateTime, String updateUserId, Integer recordFlag) {
        this.logId = logId;
        this.userId = userId;
        this.ipAddr = ipAddr;
        this.recordTime = recordTime;
        this.operateDesc = operateDesc;
        this.operateType = operateType;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
        this.recordFlag = recordFlag;
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

    public String getOperateDesc() {
        return operateDesc;
    }

    public void setOperateDesc(String operateDesc) {
        this.operateDesc = operateDesc;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Integer recordFlag) {
        this.recordFlag = recordFlag;
    }

    @Override
    public String toString() {
        return "OperateLog{" +
                "logId='" + logId + '\'' +
                ", userId='" + userId + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", recordTime=" + recordTime +
                ", operateDesc='" + operateDesc + '\'' +
                ", operateType='" + operateType + '\'' +
                ", updateTime=" + updateTime +
                ", updateUserId='" + updateUserId + '\'' +
                ", recordFlag=" + recordFlag +
                '}';
    }
}
