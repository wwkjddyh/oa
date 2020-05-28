package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 类别信息
 * @author Feng
 * @date 2019/03/14
 */
public class Category implements Serializable {

    private static final long serialVersionUID = -4727810835185743994L;
    
    /**
     * 唯一标识
     */
    private String recordId;

    /**
     * 类型唯一标识
     */
    private String typeId;

    /**
     * 分类类型名称
     */
    private String typeName;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
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

    public Category() {
    }

    public Category(String recordId, String typeId, String typeName,
                    String name, String desc, String recordTime, Integer flag) {
        this.recordId = recordId;
        this.typeId = typeId;
        this.typeName = typeName;
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
        return "Category{" +
                "recordId='" + recordId + '\'' +
                ", typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", flag=" + flag +
                '}';
    }
}
