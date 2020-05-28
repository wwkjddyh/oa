package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 用户-角色信息
 * @author Feng
 * @date 2019/03/01
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = 8417723439387329691L;

    /**
     * 主键，唯一标识
     */
    private String recordId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 用户名(不需要存入数据库)
     */
    private String userName;

    /**
     * 角色名称(不需要存入数据库)
     */
    private String roleName;

    /**
     * 信息录入时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String recordTime;

    /**
     * 检索关键字
     */
    private String key;

    public UserRole() {
    }

    public UserRole(String recordId, String userId, String roleId, String recordTime) {
        this.recordId = recordId;
        this.userId = userId;
        this.roleId = roleId;
        this.recordTime = recordTime;
    }

    public UserRole(String recordId, String userId, String roleId, String userName,
                    String roleName, String recordTime) {
        this.recordId = recordId;
        this.userId = userId;
        this.roleId = roleId;
        this.userName = userName;
        this.roleName = roleName;
        this.recordTime = recordTime;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "recordId='" + recordId + '\'' +
                ", userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", userName='" + userName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", recordTime='" + recordTime + '\'' +
                '}';
    }
}
