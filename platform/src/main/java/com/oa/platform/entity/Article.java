package com.oa.platform.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 文章信息
 * @author Feng
 * @date 2020/03/16
 */
public class Article implements Serializable {

    private static final long serialVersionUID = -562132419649815022L;

    /**
     * 唯一标识
     */
    private String recordId;

    /**
     * 类别唯一标识
     */
    private String categoryId;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介/摘要
     */
    private String intro;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签
     */
    private String tags;

    /**
     * (引用)来源
     */
    private String source;

    /**
     * 原始作者(可为空)
     */
    private String authorName;

    /**
     * (来源)网站
     */
    private String sourceSite;

    /**
     * 创建者唯一标识
     */
    private String creatorId;

    /**
     * 创建者名称
     */
    private String creatorName;

    /**
     * 更新者唯一标识
     */
    private String updatorId;

    /**
     * 更新者名称
     */
    private String updatorName;

    /**
     * 修改时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String updateTime;

    /**
     * 记录时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String recordTime;

    /**
     * 信息标识(0，逻辑删除;1，正常)
     */
    private Integer flag;

    /**
     * 评论数
     */
    private Long commentsCount = 0L;

    /**
     * 浏览数
     */
    private Long viewCount = 0L;

    /**
     * 点赞数
     */
    private Long likeCount = 0L;

    /**
     * 臭鸡蛋数
     */
    private Long stinkyEgg = 0L;

    /**
     * 搜索关键字(不需要存储入库)
     */
    private String key;

    /**
     * 创建者ID列表(查询用)
     */
    private List<String> creatorIds;

    /**
     * 简报发送者ID
     */
    private String senderId;
    private Integer approve;
    

	public Integer getApprove() {
		return approve;
	}

	public void setApprove(Integer approve) {
		this.approve = approve;
	}

	public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSourceSite() {
        return sourceSite;
    }

    public void setSourceSite(String sourceSite) {
        this.sourceSite = sourceSite;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getStinkyEgg() {
        return stinkyEgg;
    }

    public void setStinkyEgg(Long stinkyEgg) {
        this.stinkyEgg = stinkyEgg;
    }

    public List<String> getCreatorIds() {
        return creatorIds;
    }

    public void setCreatorIds(List<String> creatorIds) {
        this.creatorIds = creatorIds;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Article() {
    }

    public Article(String recordId, String categoryId, String categoryName, String title, String intro, String content,
                   String tags, String source, String authorName, String sourceSite, String creatorId, String creatorName,
                   String updatorId, String updatorName, String updateTime, String recordTime, Integer flag,
                   Long commentsCount, Long viewCount, Long likeCount, Long stinkyEgg) {
        this.recordId = recordId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.title = title;
        this.intro = intro;
        this.content = content;
        this.tags = tags;
        this.source = source;
        this.authorName = authorName;
        this.sourceSite = sourceSite;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.updatorId = updatorId;
        this.updatorName = updatorName;
        this.updateTime = updateTime;
        this.recordTime = recordTime;
        this.flag = flag;
        this.commentsCount = commentsCount;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.stinkyEgg = stinkyEgg;
    }

    @Override
    public String toString() {
        return "Article{" +
                "recordId='" + recordId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", content='" + content + '\'' +
                ", tags='" + tags + '\'' +
                ", source='" + source + '\'' +
                ", authorName='" + authorName + '\'' +
                ", sourceSite='" + sourceSite + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", updatorId='" + updatorId + '\'' +
                ", updatorName='" + updatorName + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", flag=" + flag +
                ", commentsCount=" + commentsCount +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", stinkyEgg=" + stinkyEgg +
                '}';
    }
}
