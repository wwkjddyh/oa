package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 用户角色模块(仅用于数据库查询)
 * @author Feng
 * @date 2019/04/22
 */
public class UserRoleModule implements Serializable {

    private static final long serialVersionUID = -3515628561994834957L;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 角色Id
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 模块Id
     */
    private String moduleId;

    /**
     * 上级模块id
     */
    private String parentModuleId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块全名(包含父模块名称,以'>'分割)
     */
    private String fullModuleName;

    /**
     * 检索关键字
     */
    private String key;

    public UserRoleModule() {
    }

    public UserRoleModule(String userId, String userName, String userNickname, String roleId, String roleName,
                          String moduleId, String parentModuleId, String moduleName, String fullModuleName) {
        this.userId = userId;
        this.userName = userName;
        this.userNickname = userNickname;
        this.roleId = roleId;
        this.roleName = roleName;
        this.moduleId = moduleId;
        this.parentModuleId = parentModuleId;
        this.moduleName = moduleName;
        this.fullModuleName = fullModuleName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
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

    public String getParentModuleId() {
        return parentModuleId;
    }

    public void setParentModuleId(String parentModuleId) {
        this.parentModuleId = parentModuleId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "UserRoleModule{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", parentModuleId='" + parentModuleId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", fullModuleName='" + fullModuleName + '\'' +
                '}';
    }
}
