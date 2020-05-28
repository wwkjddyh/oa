package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 字典信息
 * @author Feng
 * @date 2019/03/01
 */
public class Dict implements Serializable {

    private static final long serialVersionUID = 5739444773386908852L;

    /**
     * 主键，唯一标识
     */
    private String dictId;

    /**
     * 类型
     */
    private String dictType;

    /**
     * 名称
     */
    private String dictName;

    /**
     * 备注
     */
    private String dictRemark;

    /**
     * 信息录入时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String recordTime;

    /**
     * 信息标识(1,正常;0,删除;)
     */
    private Integer recordFlag;

    /**
     * 搜索关键字
     */
    private String key;

    public Dict() {
    }

    public Dict(String dictId, String dictType, String dictName, String dictRemark, String recordTime, Integer recordFlag) {
        this.dictId = dictId;
        this.dictType = dictType;
        this.dictName = dictName;
        this.dictRemark = dictRemark;
        this.recordTime = recordTime;
        this.recordFlag = recordFlag;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictRemark() {
        return dictRemark;
    }

    public void setDictRemark(String dictRemark) {
        this.dictRemark = dictRemark;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
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

    @Override
    public String toString() {
        return "Dict{" +
                "dictId='" + dictId + '\'' +
                ", dictType='" + dictType + '\'' +
                ", dictName='" + dictName + '\'' +
                ", dictRemark='" + dictRemark + '\'' +
                ", recordTime=" + recordTime +
                ", recordFlag=" + recordFlag +
                '}';
    }
}
