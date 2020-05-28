package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 模块信息
 * @author Feng
 * @date 2019/03/01
 */
public class Module implements Serializable {

    private static final long serialVersionUID = 4898344622978889140L;

    /**
     * 模块id，唯一标识
     */
    private String moduleId;

    /**
     * 父级模块id
     */
    private String parentId;

    /**
     * 模块名称,唯一
     */
    private String moduleName;

    /**
     * 模块描述
     */
    private String moduleDesc;

    /**
     * 链接地址
     */
    private String moduleUrl;

    /**
     * 是否为叶子(0,不是叶子--即有子级菜单；1，是叶子)
     */
    private Integer isLeaf;

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
     * 信息标识(1,正常;0,删除;-1,冻结)
     */
    private Integer recordFlag;

    /**
     * 模块全名(包含父模块名称,以'>'分割)
     */
    private String fullModuleName;

    /**
     * 模块代码
     */
    private String moduleCode;

    /**
     * 排序(不为负数的整数)
     */
    private Integer order;

    /**
     * 模块图标
     */
    private String moduleIcon;

    /**
     * 模块样式(CSS)
     */
    private String moduleStyle;

    /**
     * 是否为菜单(0,否;1,是)
     */
    private Integer isMenu;

    /**
     * 模块Logo图片(或icon图片)链接地址或名称
     */
    private String moduleLogo;

    /**
     * 搜索关键字
     */
    private String key;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFullModuleName() {
        return fullModuleName;
    }

    public void setFullModuleName(String fullModuleName) {
        this.fullModuleName = fullModuleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    public String getModuleStyle() {
        return moduleStyle;
    }

    public void setModuleStyle(String moduleStyle) {
        this.moduleStyle = moduleStyle;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public String getModuleLogo() {
        return moduleLogo;
    }

    public void setModuleLogo(String moduleLogo) {
        this.moduleLogo = moduleLogo;
    }

    public Module() {
    }

    public Module(String moduleId, String parentId, String moduleName, String moduleDesc, String moduleUrl,
                  Integer isLeaf, String recordTime, String updateTime, String updateUserId, Integer recordFlag,
                  String fullModuleName, String moduleCode, Integer order, String moduleIcon, String moduleStyle,
                  Integer isMenu, String moduleLogo) {
        this.moduleId = moduleId;
        this.parentId = parentId;
        this.moduleName = moduleName;
        this.moduleDesc = moduleDesc;
        this.moduleUrl = moduleUrl;
        this.isLeaf = isLeaf;
        this.recordTime = recordTime;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
        this.recordFlag = recordFlag;
        this.fullModuleName = fullModuleName;
        this.moduleCode = moduleCode;
        this.order = order;
        this.moduleIcon = moduleIcon;
        this.moduleStyle = moduleStyle;
        this.isMenu = isMenu;
        this.moduleLogo = moduleLogo;
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleId='" + moduleId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", moduleDesc='" + moduleDesc + '\'' +
                ", moduleUrl='" + moduleUrl + '\'' +
                ", isLeaf=" + isLeaf +
                ", recordTime='" + recordTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateUserId='" + updateUserId + '\'' +
                ", recordFlag=" + recordFlag +
                ", fullModuleName='" + fullModuleName + '\'' +
                ", moduleCode='" + moduleCode + '\'' +
                ", order='" + order + '\'' +
                ", moduleIcon='" + moduleIcon + '\'' +
                ", moduleStyle='" + moduleStyle + '\'' +
                ", isMenu='" + isMenu + '\'' +
                ", moduleLogo='" + moduleLogo + '\'' +
                '}';
    }
}
