package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 用户详细信息
 * @author Feng
 * @date 2019/03/01
 */
public class UserDtl implements Serializable {

    /**
     * 主键，唯一标识(与t_user表的user_id一致)
     */
    private String userId;

    /**
     * 用户地址
     */
    private String userAddr;

    /**
     * 用户联系电话
     */
    private String userPhone;

    /**
     * 用户手机号码
     */
    private String userMobile;

    /**
     * 用户电子邮箱地址
     */
    private String userEmail;

    /**
     * 用户微信账号
     */
    private String userWechat;

    /**
     * 用户支付宝账号
     */
    private String userAlipay;

    /**
     * 用户微博账号
     */
    private String userWeibo;

    /**
     * 用户博客网址
     */
    private String userBlog;

    /**
     * 用户QQ号
     */
    private String userQq;

    /**
     * 个人站点网址
     */
    private String userSite;

    /**
     * 信息录入时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String recordTime;

    /**
     * 信息修改时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String updateTime;

    /**
     * 搜索关键字
     */
    private String key;

    public UserDtl() {
    }

    public UserDtl(String userId, String userAddr, String userPhone, String userMobile, String userEmail,
                      String userWechat, String userAlipay, String userWeibo, String userBlog, String userQq,
                      String userSite, String recordTime, String updateTime) {
        this.userId = userId;
        this.userAddr = userAddr;
        this.userPhone = userPhone;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.userWechat = userWechat;
        this.userAlipay = userAlipay;
        this.userWeibo = userWeibo;
        this.userBlog = userBlog;
        this.userQq = userQq;
        this.userSite = userSite;
        this.recordTime = recordTime;
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserWechat() {
        return userWechat;
    }

    public void setUserWechat(String userWechat) {
        this.userWechat = userWechat;
    }

    public String getUserAlipay() {
        return userAlipay;
    }

    public void setUserAlipay(String userAlipay) {
        this.userAlipay = userAlipay;
    }

    public String getUserWeibo() {
        return userWeibo;
    }

    public void setUserWeibo(String userWeibo) {
        this.userWeibo = userWeibo;
    }

    public String getUserBlog() {
        return userBlog;
    }

    public void setUserBlog(String userBlog) {
        this.userBlog = userBlog;
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }

    public String getUserSite() {
        return userSite;
    }

    public void setUserSite(String userSite) {
        this.userSite = userSite;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "UserDtl{" +
                "userId='" + userId + '\'' +
                ", userAddr='" + userAddr + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userWechat='" + userWechat + '\'' +
                ", userAlipay='" + userAlipay + '\'' +
                ", userWeibo='" + userWeibo + '\'' +
                ", userBlog='" + userBlog + '\'' +
                ", userQq='" + userQq + '\'' +
                ", userSite='" + userSite + '\'' +
                ", recordTime=" + recordTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
