package com.oa.platform.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户信息
 * @author Feng
 * @date 2019/03/01
 */
public class User implements UserDetails, Serializable {

    /**
     * 类型：管理员
     */
    public static final Integer TYPE_ADMIN = 1;

    /**
     * 类型：企业
     */
    public static final Integer TYPE_COMPANY = 2;

    /**
     * 类型：个人
     */
    public static final Integer TYPE_PERSON = 3;

    /**
     * 主键，唯一标识
     */
    private String userId;

    /**
     * 用户类型(1,管理员;2,企业;3,个人)
     */
    private Integer userType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 原始密码
     */
    private String userPwdOrigi;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 信息标识(1,正常;0,删除;-1,冻结)
     */
    private Integer recordFlag = 1;

    /**
     * 最近登录时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String lastLoginTime;

    /**
     * 信息录入时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String recordTime;

    /**
     * 信息修改时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String updateTime;

    /**
     * 语言配置ID
     */
    private String langConfId;

    /**
     * 搜索关键字
     */
    private String key;

    /**
     * 用户type名称
     */
    private String typeName;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 是否为管理员(搜索时使用),默认为"1，即是
     */
    private String isAdmin = "1";

    /**
     * 第三方(用户类型)，为空表示虫控系统注册用户；其他值表示第三方类型
     */
    private String thirdPartyType = "";

    /**
     * 第三方id
     */
    private String thirdPartyId = "";

    /**
     * 性别 0：未知、1：男、2：女
     */
    private String userSex = "";

    /**
     * 性别名
     */
    private String userSexName = "未知";

    /**
     * 语言名称
     */
    private String langName;

    /**
     * 语言代码
     */
    private String langCode;

    /**
     * Id数组
     */
    private List<String> ids;

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getThirdPartyType() {
        return thirdPartyType;
    }

    public void setThirdPartyType(String thirdPartyType) {
        this.thirdPartyType = thirdPartyType;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserSexName() {
        userSexName = "未知";
        if("1".equals(userSex)) {
            userSexName = "男";
        }
        else if("2".equals(userSex)) {
            userSexName = "女";
        }
        return userSexName;
    }

    public void setUserSexName(String userSexName) {
        this.userSexName = userSexName;
    }

    /**
     * 详细信息
     */
    private UserDtl userDtl;

    /**
     * 用户角色对应的所有模块信息
     */
    private List<Module> modules;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserPwdOrigi() {
        return userPwdOrigi;
    }

    public void setUserPwdOrigi(String userPwdOrigi) {
        this.userPwdOrigi = userPwdOrigi;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Integer getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Integer recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public UserDtl getUserDtl() {
        return userDtl;
    }

    public void setUserDtl(UserDtl userDtl) {
        this.userDtl = userDtl;
    }

    public String getLangConfId() {
        return langConfId;
    }

    public void setLangConfId(String langConfId) {
        this.langConfId = langConfId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getTypeName() {
        typeName = "";
        if(userType != null) {
            if (userType.intValue() == TYPE_ADMIN.intValue()) {
                typeName = "管理员";
            }
            else if(userType.intValue() == TYPE_PERSON.intValue()) {
                typeName = "个人";
            }
            else if(userType.intValue() == TYPE_COMPANY.intValue()) {
                typeName = "企业";
            }
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        statusName = "";
        if(recordFlag != null) {
            switch (recordFlag.intValue()) {
                case 1:
                    statusName = "正常";
                    break;
                case 0:
                    statusName = "删除";
                    break;
                case -1:
                    statusName = "冻结";
                    break;
                default: break;
            }
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public User() {
    }

    public User(String userId, Integer userType, String userName, String userPwd, String userNickname,
                Integer recordFlag, String lastLoginTime, String recordTime, String updateTime, String langConfId,
                String userSex) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userNickname = userNickname;
        this.recordFlag = recordFlag;
        this.lastLoginTime = lastLoginTime;
        this.recordTime = recordTime;
        this.updateTime = updateTime;
        this.langConfId = langConfId;
        this.userSex = userSex;
    }

    public User(String userId, Integer userType, String userName, String userPwd, String userPwdOrigi,
                String userNickname, Integer recordFlag, String lastLoginTime, String recordTime,
                String updateTime, String langConfId, String thirdPartyType, String thirdPartyId, String userSex) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userPwdOrigi = userPwdOrigi;
        this.userNickname = userNickname;
        this.recordFlag = recordFlag;
        this.lastLoginTime = lastLoginTime;
        this.recordTime = recordTime;
        this.updateTime = updateTime;
        this.langConfId = langConfId;
        this.thirdPartyType = thirdPartyType;
        this.thirdPartyId = thirdPartyId;
        this.userSex = userSex;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userType=" + userType +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userPwdOrigi='" + userPwdOrigi + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", recordFlag=" + recordFlag +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", langConfId='" + langConfId + '\'' +
                ", thirdPartyType='" + thirdPartyType + '\'' +
                ", thirdPartyId='" + thirdPartyId + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userSexName='" + userSexName + '\'' +
                ", userDtl=" + userDtl +
                ", authorities=" + authorities +
                '}';
    }

    private List<Role> authorities;


    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userPwd;
    }

    @Override
    public String getUsername() {
        return this.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
