package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 角色-模块信息
 * @author Feng
 * @date 2019/03/01
 */
public class RoleModule implements Serializable {

    private static final long serialVersionUID = -2146117662433246185L;

    /**
     * 主键，唯一标识
     */
    private String recordId;

    /**
     * 用户id
     */
    private String roleId;

    /**
     * 模块id
     */
    private String moduleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块全名(包含父模块名称,以'>'分割)
     */
    private String fullModuleName;

    /**
     * 信息录入时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String recordTime;

    /**
     * 检索关键字
     */
    private String key;

    public RoleModule() {
    }

    public RoleModule(String recordId, String roleId, String moduleId, String recordTime) {
        this.recordId = recordId;
        this.roleId = roleId;
        this.moduleId = moduleId;
        this.recordTime = recordTime;
    }

    public RoleModule(String recordId, String roleId, String moduleId, String roleName,
                      String moduleName, String fullModuleName, String recordTime) {
        this.recordId = recordId;
        this.roleId = roleId;
        this.moduleId = moduleId;
        this.roleName = roleName;
        this.moduleName = moduleName;
        this.fullModuleName = fullModuleName;
        this.recordTime = recordTime;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFullModuleName() {
        return fullModuleName;
    }

    public void setFullModuleName(String fullModuleName) {
        this.fullModuleName = fullModuleName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "RoleModule{" +
                "recordId='" + recordId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", fullModuleName='" + fullModuleName + '\'' +
                ", recordTime='" + recordTime + '\'' +
                '}';
    }
}
