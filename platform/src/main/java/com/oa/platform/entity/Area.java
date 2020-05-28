package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 地域信息
 * @author jianbo.feng
 * @date 2020/03/14
 */
public class Area implements Serializable {

    private static final long serialVersionUID = -3068970711629131582L;

    /**
     * 信息唯一标识
     */
    private String areaId;

    /**
     * 父级id(为null或"")
     */
    private String parentId;

    /**
     * 父级区域名称{不需要添加入库}
     */
    private String parentName;

    /**
     * 名称
     */
    private String areaName;

    /**
     * 描述
     */
    private String areaDesc;

    /**
     * 信息标识符（0，删除；1，正常）
     */
    private Integer recordFlag;

    /**
     * 查询关键词
     */
    private String key;

    /**
     * 地域全名(包括上级地域名称，以">"分隔)
     */
    private String fullName;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaDesc() {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Area() {
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaId='" + areaId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", areaDesc='" + areaDesc + '\'' +
                ", recordFlag=" + recordFlag +
                '}';
    }
}
