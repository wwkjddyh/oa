package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 资源下载(记录)
 * @author jianbo.feng
 * @date 2020/03/14
 */
public class ResDl implements Serializable {

    private static final long serialVersionUID = -231519262276606468L;

    /**
     * 唯一标识
     */
    private String recordId;

    /**
     * 资源ID
     */
    private String resId;

    /**
     * (下载)用户ID
     */
    private String userId;

    /**
     * 下载时间
     */
    private String dlTime;

    /**
     * 信息状态标识
     */
    private Integer recordFlag;

    /**
     * 查询关键字
     */
    private String key;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源原始名称
     */
    private String resOriginalName;

    /**
     * (下载)用户姓名
     */
    private String userName;

    /**
     * 资源类型ID(查询时使用)
     */
    private String typeId;

    /**
     * 资源类型名称(查询时使用)
     */
    private String typeName;

    /**
     * 关联信息ID(查询时使用)
     */
    private String assId;

    /**
     * 关联类型ID(查询时使用)
     */
    private String assTypeId;

    /**
     * 关联信息标题或名称(查询时使用)
     */
    private String assTitle;

    /**
     * 关联信息类型名称(查询时使用)
     */
    private String assTypeName;

    /**
     * 资源信息
     */
    private Res res;

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDlTime() {
        return dlTime;
    }

    public void setDlTime(String dlTime) {
        this.dlTime = dlTime;
    }

    public Integer getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Integer recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResOriginalName() {
        return resOriginalName;
    }

    public void setResOriginalName(String resOriginalName) {
        this.resOriginalName = resOriginalName;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAssId() {
        return assId;
    }

    public void setAssId(String assId) {
        this.assId = assId;
    }

    public String getAssTypeId() {
        return assTypeId;
    }

    public void setAssTypeId(String assTypeId) {
        this.assTypeId = assTypeId;
    }

    public String getAssTitle() {
        return assTitle;
    }

    public void setAssTitle(String assTitle) {
        this.assTitle = assTitle;
    }

    public String getAssTypeName() {
        return assTypeName;
    }

    public void setAssTypeName(String assTypeName) {
        this.assTypeName = assTypeName;
    }

    public ResDl() {
    }

    @Override
    public String toString() {
        return "ResDl{" +
                "recordId='" + recordId + '\'' +
                ", resId='" + resId + '\'' +
                ", userId='" + userId + '\'' +
                ", dlTime='" + dlTime + '\'' +
                ", recordFlag=" + recordFlag +
                ", resName='" + resName + '\'' +
                ", resOriginalName='" + resOriginalName + '\'' +
                ", userName='" + userName + '\'' +
                ", typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", assId='" + assId + '\'' +
                ", assTypeId='" + assTypeId + '\'' +
                ", assTitle='" + assTitle + '\'' +
                ", assTypeName='" + assTypeName + '\'' +
                '}';
    }
}
