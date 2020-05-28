package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 语言配置
 * @author Feng
 * @date 2019/03/01
 */
public class LangConf implements Serializable {

    private static final long serialVersionUID = -5068061833119291745L;

    /**
     * 主键，唯一标识
     */
    private String recordId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 代码
     */
    private String code;

    /**
     * 信息录入时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String recordTime;

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

    /**
     * 搜索关键字
     */
    private String key;

    public LangConf() {
    }

    public LangConf(String recordId, String name, String desc, String code, String recordTime,
                    String updateTime, String updateUserId, Integer recordFlag) {
        this.recordId = recordId;
        this.name = name;
        this.desc = desc;
        this.code = code;
        this.recordTime = recordTime;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
        this.recordFlag = recordFlag;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "LangConf{" +
                "recordId='" + recordId + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", code='" + code + '\'' +
                ", recordTime=" + recordTime +
                ", updateTime=" + updateTime +
                ", updateUserId='" + updateUserId + '\'' +
                ", recordFlag=" + recordFlag +
                '}';
    }
}
