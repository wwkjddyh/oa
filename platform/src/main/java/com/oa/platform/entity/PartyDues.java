package com.oa.platform.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 党费(缴纳)信息
 * @author jianbo.feng
 * @date 2020/03/16
 */
public class PartyDues implements Serializable {

    /**
     * 唯一标识
     */
    private String recordId;

    /**
     * 信息状态标识(0,删除;1,正常)
     */
    private Integer recordFlag;

    /**
     * (党员)用户ID
     */
    private String userId;

    /**
     * 缴纳期(如：上缴哪季度党费)
     */
    private String payPeriod;

    /**
     * 缴纳时间
     */
    private String payTime;

    /**
     * 缴纳金额
     */
    private BigDecimal payAmount;

    /**
     * 信息录入时间
     */
    private String recordTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 编辑者ID
     */
    private String updatorId;

    /**
     * 编辑时间
     */
    private String updatetTime;

    /**
     * 查询关键字
     */
    private String key;

    /**
     * 党员姓名
     */
    private String userName;

    /**
     * 党员昵称
     */
    private String userNickname;

    /**
     * 性别 0：未知、1：男、2：女
     */
    private String userSex = "";

    /**
     * 用户所属组织名称
     */
    private String userOrgName = "";

    /**
     * 更新者姓名
     */
    private String updatorName;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Integer getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Integer recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserOrgName() {
        return userOrgName;
    }

    public void setUserOrgName(String userOrgName) {
        this.userOrgName = userOrgName;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatetTime() {
        return updatetTime;
    }

    public void setUpdatetTime(String updatetTime) {
        this.updatetTime = updatetTime;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public PartyDues() {
    }

    @Override
    public String toString() {
        return "PartyDues{" +
                "recordId='" + recordId + '\'' +
                ", recordFlag=" + recordFlag +
                ", userId='" + userId + '\'' +
                ", payPeriod='" + payPeriod + '\'' +
                ", payTime='" + payTime + '\'' +
                ", payAmount=" + payAmount +
                ", recordTime='" + recordTime + '\'' +
                ", remark='" + remark + '\'' +
                ", updatorId='" + updatorId + '\'' +
                ", updatetTime='" + updatetTime + '\'' +
                ", userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userOrgName='" + userOrgName + '\'' +
                ", updatorName='" + updatorName + '\'' +
                '}';
    }
}
