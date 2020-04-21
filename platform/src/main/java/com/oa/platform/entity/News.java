package com.oa.platform.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 消息
 * @author jianbo.feng
 * @date 2020/03/12
 */
public class News implements Serializable {

    /**
     * 记录ID
     */
    private String recordId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 标签(可为空)
     */
    private String tags;

    /**
     * 消息备注(可为空)
     */
    private String remark;

    /**
     * 类型ID
     */
    private String typeId;

    /**
     * 是否回执(0,否;1,是)
     */
    private Integer isReceipt;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 截止时间
     */
    private String endTime;

    /**
     * 接收者类型(0,用户;1,角色)
     */
    private Integer receiverType;

    /**
     * 接收者ID(可以是用户ID组或角色ID组, 以','分割)
     */
    private String receiverId;

    /**
     * 信息录入时间
     */
    private String recordTime;

    /**
     * 信息录入者Id
     */
    private String recordUserId;

    /**
     * 消息录入者名称
     */
    private String recordUserName;

    /**
     * 信息标识(1,正常;0,删除;)
     */
    private Integer recordFlag;

    /**
     * 信息修改时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String updateTime;

    /**
     * 信息修改者id
     */
    private String updateUserId;

    /**
     * 信息修改者名称
     */
    private String updateUserName;

    /**
     * (是否)发送短信(1,是;0,否;)
     */
    private Integer sendSms;

    /**
     * (是否)发送邮件(1,是;0,否;)
     */
    private Integer sendMail;

    /**
     * 搜索关键字
     */
    private String key;

    /**
     * 类型名称(数据库查询之用)
     */
    private String typeName;

    /**
     * 接收角色列表(数据显示之用)
     */
    private List<Role> receiveRoles;

    /**
     * 接收用户列表(数据显示之用)
     */
    private List<User> receiveUsers;

    /**
     * <当前用户>是否已浏览(用于判定用户已查看该消息)
     */
    private Integer isViewed;
    private String fileUrl;
    private String fileName;
    
    public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
     * <当前>浏览者ID
     */
    private String viewerId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(Integer isReceipt) {
        this.isReceipt = isReceipt;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Integer receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(String recordUserId) {
        this.recordUserId = recordUserId;
    }

    public String getRecordUserName() {
        return recordUserName;
    }

    public void setRecordUserName(String recordUserName) {
        this.recordUserName = recordUserName;
    }

    public Integer getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Integer recordFlag) {
        this.recordFlag = recordFlag;
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

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Role> getReceiveRoles() {
        return receiveRoles;
    }

    public void setReceiveRoles(List<Role> receiveRoles) {
        this.receiveRoles = receiveRoles;
    }

    public List<User> getReceiveUsers() {
        return receiveUsers;
    }

    public void setReceiveUsers(List<User> receiveUsers) {
        this.receiveUsers = receiveUsers;
    }

    public Integer getSendSms() {
        return sendSms;
    }

    public void setSendSms(Integer sendSms) {
        this.sendSms = sendSms;
    }

    public Integer getSendMail() {
        return sendMail;
    }

    public void setSendMail(Integer sendMail) {
        this.sendMail = sendMail;
    }

    public Integer getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(Integer isViewed) {
        this.isViewed = isViewed;
    }

    public String getViewerId() {
        return viewerId;
    }

    public void setViewerId(String viewerId) {
        this.viewerId = viewerId;
    }

    public News() {
    }

    @Override
    public String toString() {
        return "News{" +
                "recordId='" + recordId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags='" + tags + '\'' +
                ", remark='" + remark + '\'' +
                ", typeId='" + typeId + '\'' +
                ", isReceipt=" + isReceipt +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", receiverType=" + receiverType +
                ", receiverId='" + receiverId + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", recordUserId='" + recordUserId + '\'' +
                ", recordUserName='" + recordUserName + '\'' +
                ", recordFlag=" + recordFlag +
                ", updateTime='" + updateTime + '\'' +
                ", updateUserId='" + updateUserId + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", sendSms=" + sendSms +
                ", sendMail=" + sendMail +
                ", typeName='" + typeName + '\'' +
                ", receiveRoles=" + receiveRoles +
                ", receiveUsers=" + receiveUsers +
                '}';
    }
}
