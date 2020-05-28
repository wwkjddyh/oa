package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 类别类型信息
 * @author Feng
 * @date 2019/03/14
 */
public class CategoryType implements Serializable {

    private static final long serialVersionUID = -7914520452407839509L;

    /**
     * 信息标识
     */
    private String recordId;

    /**
     * 类别类型名称
     */
    private String name;

    /**
     * 类别类型描述
     */
    private String desc;

    /**
     * 记录时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String recordTime;

    /**
     * 信息标识(0，逻辑删除;1，正常)
     */
    private Integer flag;

    /**
     * 搜索关键字
     */
    private String key;

    public CategoryType() {
    }

    public CategoryType(String recordId, String name, String desc, String recordTime, Integer flag) {
        this.recordId = recordId;
        this.name = name;
        this.desc = desc;
        this.recordTime = recordTime;
        this.flag = flag;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "CategoryType{" +
                "recordId='" + recordId + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", flag=" + flag +
                '}';
    }
}
