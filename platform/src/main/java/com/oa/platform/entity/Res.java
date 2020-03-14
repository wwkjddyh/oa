package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 资源信息
 * @author jianbo.feng
 * @date 2020/03/14
 */
public class Res implements Serializable {

    /**
     * 唯一标识
     */
    private String recordId;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源类型ID
     */
    private String typeId;

    /**
     * 关联信息ID
     */
    private String assId;

    /**
     * 关联信息类型ID
     */
    private String assTypeId;

    /**
     * 原始名称
     */
    private String originalName;

    /**
     * 信息录入时间
     */
    private String recordTime;

    /**
     * 信息状态标识
     */
    private Integer recordFlag;

    /**
     * 资源大小
     */
    private String resSize;

    /**
     * 资源发布者
     */
    private String announcerId;

    /**
     * 资源作者
     */
    private String resAuthor;

    /**
     * 资源来源
     */
    private String resSrc;

    /**
     * 资源简介
     */
    private String resIntro;

    /**
     * 资源说明
     */
    private String resDesc;

    /**
     * 资源标签
     */
    private String resTags;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 修改者ID
     */
    private String editorId;

    /**
     * 访问链接
     */
    private String accessUrl;

    /**
     * 审核者ID
     */
    private String auditorId;

    /**
     * 审核时间
     */
    private String auditTime;

    /**
     * 审核状态(0,未审核;1,审核中;2,已审核)
     */
    private Integer auditStatus;

    /**
     * 查询关键字(查询时使用)
     */
    private String key;

    /**
     * 资源类型名称(查询时使用)
     */
    private String typeName;

    /**
     * 关联信息标题或名称(查询时使用)
     */
    private String assTitle;

    /**
     * 关联信息类型名称(查询时使用)
     */
    private String assTypeName;

    /**
     * 资源发布者姓名(查询时使用)
     */
    private String announcerName;

    /**
     * 资源修改者姓名(查询时使用)
     */
    private String editorName;

    /**
     * 资源审核者姓名(查询时使用)
     */
    private String auditorName;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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

    public String getResSize() {
        return resSize;
    }

    public void setResSize(String resSize) {
        this.resSize = resSize;
    }

    public String getAnnouncerId() {
        return announcerId;
    }

    public void setAnnouncerId(String announcerId) {
        this.announcerId = announcerId;
    }

    public String getResAuthor() {
        return resAuthor;
    }

    public void setResAuthor(String resAuthor) {
        this.resAuthor = resAuthor;
    }

    public String getResSrc() {
        return resSrc;
    }

    public void setResSrc(String resSrc) {
        this.resSrc = resSrc;
    }

    public String getResIntro() {
        return resIntro;
    }

    public void setResIntro(String resIntro) {
        this.resIntro = resIntro;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public String getResTags() {
        return resTags;
    }

    public void setResTags(String resTags) {
        this.resTags = resTags;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getEditorId() {
        return editorId;
    }

    public void setEditorId(String editorId) {
        this.editorId = editorId;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getAnnouncerName() {
        return announcerName;
    }

    public void setAnnouncerName(String announcerName) {
        this.announcerName = announcerName;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Res() {
    }

    @Override
    public String toString() {
        return "Res{" +
                "recordId='" + recordId + '\'' +
                ", resName='" + resName + '\'' +
                ", typeId='" + typeId + '\'' +
                ", assId='" + assId + '\'' +
                ", assTypeId='" + assTypeId + '\'' +
                ", originalName='" + originalName + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", recordFlag=" + recordFlag +
                ", resSize='" + resSize + '\'' +
                ", announcerId='" + announcerId + '\'' +
                ", resAuthor='" + resAuthor + '\'' +
                ", resSrc='" + resSrc + '\'' +
                ", resIntro='" + resIntro + '\'' +
                ", resDesc='" + resDesc + '\'' +
                ", resTags='" + resTags + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", editorId='" + editorId + '\'' +
                ", accessUrl='" + accessUrl + '\'' +
                ", auditorId='" + auditorId + '\'' +
                ", auditTime='" + auditTime + '\'' +
                ", auditStatus=" + auditStatus +
                ", typeName='" + typeName + '\'' +
                ", assTitle='" + assTitle + '\'' +
                ", assTypeName='" + assTypeName + '\'' +
                ", announcerName='" + announcerName + '\'' +
                ", editorName='" + editorName + '\'' +
                ", auditorName='" + auditorName + '\'' +
                '}';
    }
}
