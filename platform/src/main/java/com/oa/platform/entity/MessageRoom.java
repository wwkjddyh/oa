package com.oa.platform.entity;

import java.io.Serializable;

/**
 * (消息)房间或群信息
 * @author jianbo.feng
 * @create 2020/04/15
 */
public class MessageRoom implements Serializable {

    /**
     * 唯一标识
     */
    private String roomId;

    /**
     * 消息类型ID
     */
    private String categoryId;

    /**
     * 房间标题或名称
     */
    private String roomTitle;

    /**
     * 房间介绍
     */
    private String roomIntro;

    /**
     * 房间公告或通告
     */
    private String roomBulletin;

    /**
     * 信息录入者ID
     */
    private String recorderId;

    /**
     * 信息录入时间
     */
    private String recordTime;

    /**
     * 信息更新者ID
     */
    private String updaterId;

    /**
     * 信息更新时间
     */
    private String updateTime;


    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 录入者姓名
     */
    private String recorderName;

    /**
     * 更新者姓名
     */
    private String updaterName;

    /**
     * 关键字
     */
    private String key;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomIntro() {
        return roomIntro;
    }

    public void setRoomIntro(String roomIntro) {
        this.roomIntro = roomIntro;
    }

    public String getRoomBulletin() {
        return roomBulletin;
    }

    public void setRoomBulletin(String roomBulletin) {
        this.roomBulletin = roomBulletin;
    }

    public String getRecorderId() {
        return recorderId;
    }

    public void setRecorderId(String recorderId) {
        this.recorderId = recorderId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRecorderName() {
        return recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MessageRoom() {
    }

    public MessageRoom(String roomId, String categoryId, String roomTitle, String roomIntro, String roomBulletin,
                       String recorderId, String recordTime, String updaterId, String updateTime) {
        this.roomId = roomId;
        this.categoryId = categoryId;
        this.roomTitle = roomTitle;
        this.roomIntro = roomIntro;
        this.roomBulletin = roomBulletin;
        this.recorderId = recorderId;
        this.recordTime = recordTime;
        this.updaterId = updaterId;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MessageRoom{" +
                "roomId='" + roomId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", roomTitle='" + roomTitle + '\'' +
                ", roomIntro='" + roomIntro + '\'' +
                ", roomBulletin='" + roomBulletin + '\'' +
                ", recorderId='" + recorderId + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", updaterId='" + updaterId + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", recorderName='" + recorderName + '\'' +
                ", updaterName='" + updaterName + '\'' +
                '}';
    }
}
