package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 预备党员信息
 * @author jianbo.feng
 * @create 2020/04/18
 */
public class PrePartyMemeber implements Serializable {

    /**
     * 唯一标识, 即预备党员ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 组织ID
     */
    private String orgId;

    /**
     * 阶段（1、申请入党；2、入党积极分子的确定；3、发展对象的确定和考核；4、预备党员的接收；5、预备党员的教育考核和转正）
     */
    private Integer stage;

    /**
     * 信息标识(1,正常;0,删除;-1,冻结)
     */
    private String recordFlag;

    /**
     * 信息录入者ID
     */
    private String recorderId;

    /**
     * 信息更新者ID
     */
    private String updaterId;

    /**
     * 信息录入时间
     */
    private String recordTime;

    /**
     * 信息更新时间
     */
    private String updateTime;

    /**
     * 关键字
     */
    private String key;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getRecorderId() {
        return recorderId;
    }

    public void setRecorderId(String recorderId) {
        this.recorderId = recorderId;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PrePartyMemeber() {
    }

    public PrePartyMemeber(String userId, String userName, String userNickname, String orgId, Integer stage,
                           String recordFlag, String recorderId, String updaterId, String recordTime,
                           String updateTime) {
        this.userId = userId;
        this.userName = userName;
        this.userNickname = userNickname;
        this.orgId = orgId;
        this.stage = stage;
        this.recordFlag = recordFlag;
        this.recorderId = recorderId;
        this.updaterId = updaterId;
        this.recordTime = recordTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PrePartyMemeber{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", orgId='" + orgId + '\'' +
                ", stage=" + stage +
                ", recordFlag='" + recordFlag + '\'' +
                ", recorderId='" + recorderId + '\'' +
                ", updaterId='" + updaterId + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
