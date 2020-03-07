package com.oa.platform.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * 角色信息
 * @author Feng
 * @date 2019/03/01
 */
public class Role implements GrantedAuthority,Serializable {

    /**
     * 角色id，唯一标识
     */
    private String roleId;

    /**
     * 角色名称,唯一
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

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
     * 搜索关键字
     */
    private String key;

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

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
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

    public Role() {
    }

    public Role(String roleId, String roleName, String roleDesc, String recordTime,
                String updateTime, String updateUserId, Integer recordFlag) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.recordTime = recordTime;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
        this.recordFlag = recordFlag;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", recordTime=" + recordTime +
                ", updateTime=" + updateTime +
                ", updateUserId='" + updateUserId + '\'' +
                ", recordFlag=" + recordFlag +
                '}';
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
