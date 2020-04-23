package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Article;
import com.oa.platform.entity.BriefSendRecord;
import com.oa.platform.entity.NewsSendRecord;
import com.oa.platform.service.ArticleService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 文章业务处理
 * @author Feng
 * @date 2020/03/16
 */
@Component
public class ArticleBiz extends BaseBiz {

    @Autowired
    private ArticleService articleService;

    /**
     * 保存文章信息
     * @param recordId 信息标识
     * @param categoryId 类别标识
     * @param title 标题
     * @param intro 简介
     * @param content 内容
     * @param tags 标签
     * @param source 来源
     * @param authorName (若文章为引用)作者姓名
     * @param sourceSite 来源站点
     * @param flag 信息标志
     * @param sendType 发送类型（默认："1"简报）
     * @param receiverIds 简报接收者ID组
     * @return
     */
    public Map<String,Object> saveArticle(String recordId, String categoryId, String title, String intro,
                                          String content, String tags, String source, String authorName,
                                          String sourceSite, String flag, String sendType, String[] receiverIds) {
        recordId = StringUtil.trim(recordId);
        categoryId = StringUtil.trim(categoryId);
        title = StringUtil.trim(title);
        intro = StringUtil.trim(intro);
        content = StringUtil.trim(content);
        tags = StringUtil.trim(tags);
        source = StringUtil.trim(source);
        authorName = StringUtil.trim(authorName);
        sourceSite = StringUtil.trim(sourceSite);
        flag = StringUtil.trim(flag, Constants.INT_NORMAL + "");
        if("".equals(categoryId) || "".equals(title) ||  "".equals(content)) {
            ret = this.getParamErrorVo();
        }
        else {
           try {
               /**
                * 发送类型：
                * 1: 简报
                */
               sendType = StringUtil.trim(sendType, "1");
               String userId = this.getUserIdOfSecurity();
               boolean isEdit = isEdit(recordId);
               Article validEntity = new Article();
               validEntity.setTitle(title);
               validEntity.setCreatorId(userId);
               validEntity.setCategoryId(categoryId);
               validEntity.setFlag(Constants.INT_NORMAL);
               List<Article> entries = articleService.find(validEntity);
               boolean isRepeat = false;
               if(entries != null && !entries.isEmpty()) {
                   if(!isEdit) {    //"新增"操作，只要有相同标题则表示重复
                       isRepeat = true;
                   }
                   else {   //"编辑"操作，若不同的信息唯一标识有相同的标题则表示重复
                       final String finalRecordId = recordId;
                       isRepeat = entries.parallelStream().anyMatch(e -> !e.getRecordId().equals(finalRecordId));
                   }
               }
               if(isRepeat) {
                   ret = this.getParamRepeatErrorVo("".equals(sendType) ? "主题" : "标题");
               }
               else {
                   Article article = new Article();
                   article.setTitle(title);
                   article.setCategoryId(categoryId);
                   article.setIntro(intro);
                   article.setContent(content);
                   article.setTags(tags);
                   article.setSource(source);
                   article.setAuthorName(authorName);
                   article.setSourceSite(sourceSite);
                   article.setFlag(Integer.parseInt(flag));
                   String dateStr = DateUtil.currDateFormat(null);

                   if(isEdit) {
                       if ("1".equals(sendType)) {
                           sendBriefs(recordId, userId, receiverIds, dateStr);
                       }
                       article.setRecordId(recordId);
                       article.setUpdatorId(userId);
                       article.setUpdateTime(dateStr);
                       articleService.update(article);
                   }
                   else {
                       recordId = StringUtil.getRandomUUID();
                       if ("1".equals(sendType)) {
                           sendBriefs(recordId, userId, receiverIds, dateStr);
                       }
                       article.setRecordId(recordId);
                       article.setCreatorId(userId);
                       article.setRecordTime(dateStr);
                       articleService.save(article);
                   }
                   ret = this.getSuccessVo("","");
               }
           }
           catch(Exception e) {
               e.printStackTrace();
               loggerError(ThreadUtil.getCurrentFullMethodName(), e);
               ret = this.getErrorVo();
           }
        }
        return ret;
    }

    /**
     * 发送简报
     * @param briefId 简报ID
     * @param receiverIds 接收者ID组
     * @param dateStr 时间
     */
    void sendBriefs(String briefId, String senderId, String[] receiverIds, String dateStr) {
        briefId = StringUtil.trim(briefId);
        senderId = StringUtil.trim(senderId);
        if (!"".equals(briefId) && !"".equals(senderId) && receiverIds != null) {
            int idsLen = receiverIds.length;
            if (idsLen > 0) {
                dateStr = StringUtil.trim(dateStr, DateUtil.currDateFormat(null));
                List<BriefSendRecord> records = Lists.newArrayList();
                for (int i = 0; i < idsLen; i ++ ) {
                    String receiverId = StringUtil.trim(receiverIds[i]);
                    if (!"".equals(receiverId)) {
                        BriefSendRecord record = new BriefSendRecord();
                        record.setRecordId(StringUtil.getRandomUUID());
                        record.setBriefId(briefId);
                        record.setSenderId(senderId);
                        record.setReceiverId(receiverId);
                        record.setSendTime(dateStr);
                        record.setSenderRemark("");
                        record.setReceiverRemark("");
                        record.setStatus(Constants.UN_VIEWED);
                        record.setRecordFlag(Constants.INT_NORMAL);
                        records.add(record);
                    }
                }
                if (!records.isEmpty()) {
                    articleService.batchSaveBriefSendRecord(records);
                }
            }
        }
    }

    /**
     * 检索
     * @param recordId 信息唯一标识
     * @param categoryId 类别标识
     * @param creatorId 创建者唯一标识
     * @param updatorId 更新者唯一标识
     * @param key 关键词
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String,Object> search(String recordId, String categoryId, String creatorId,
                                     String updatorId, String key, int pageNum,int pageSize) {
        try {
            recordId = StringUtil.trim(recordId);
            Article article = new Article();
            article.setRecordId(recordId);
            article.setFlag(Constants.INT_NORMAL);
            if(!"".equals(recordId)) {
                List<Article> articles = articleService.find(article);
                if(articles == null || articles.isEmpty()) {
                    ret = this.getParamErrorVo();
                }
                else {
                    ret = this.getSuccessVo("",articles.get(0));
                }
            }
            else {
                article.setCategoryId(StringUtil.trim(categoryId));
                article.setCreatorId(StringUtil.trim(creatorId));
                article.setUpdatorId(StringUtil.trim(updatorId));
                article.setKey(StringUtil.trim(key));
                PageInfo<Article> pageInfo = articleService.search(article, pageNum, pageSize);
                ret = this.getPageInfo(pageInfo);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 根据Id更新文章状态
     * @param id 信息唯一标识
     * @param flag 信息状态标志
     * @return
     */
    public Map<String,Object> updateArticleFlagById(String id,String flag) {
        id = StringUtil.trim(id);
        flag = StringUtil.trim(flag);
        if("".equals(id) || "".equals(flag) || !StringUtil.isNumber(flag)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Article article = new Article();
                article.setRecordId(id);
                article.setFlag(Integer.parseInt(flag));
                article.setUpdatorId(this.getUserIdOfSecurity());
                article.setUpdateTime(DateUtil.currDateFormat(null));
                articleService.update(article);
                ret = this.getSuccessVo("","");
            }
            catch(Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据简报ID查询接收者ID列表
     * @param briefId 简报ID
     * @return
     */
    public Map<String, Object> getReceiverIdsByBriefId(String briefId) {
        briefId = StringUtil.trim(briefId);
        if ("".equals(briefId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                ret = this.getSuccessVo("", articleService.findReceiverIdByBriefId(briefId));
            } catch (Exception e) {
                e.printStackTrace();
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 检索简报发送信息
     * @param id 唯一标识
     * @param briefId 简报ID
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @param status 状态
     * @param recordFlag 信息标识
     * @param sendTime 发送时间
     * @param viewTime 浏览时间
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String, Object> searchBriefSendRecord(String id, String briefId, String senderId, String receiverId,
                                                     Integer status, Integer recordFlag, String sendTime,
                                                     String viewTime, String key, int pageNum, int pageSize,String categoryId) {
        id = StringUtil.trim(id);
        BriefSendRecord record = new BriefSendRecord();
        record.setRecordId(id);
        if ("".equals(id)) {
            record.setBriefId(StringUtil.trim(briefId));
            record.setSenderId(StringUtil.trim(senderId));
            record.setReceiverId(StringUtil.trim(receiverId));
            if (status != null) {
                record.setStatus(status);
            }
            if (recordFlag != null) {
                record.setRecordFlag(recordFlag);
            }
            record.setSendTime(StringUtil.trim(sendTime));
            record.setCategoryId(categoryId);
            record.setViewTime(StringUtil.trim(viewTime));
            record.setKey(StringUtil.trim(key));
            if(senderId != null && !"".equals(senderId)) {
            	PageInfo<BriefSendRecord> pageInfo = articleService.searchBriefSendRecordBySendId(record, pageNum, pageSize);
            	ret = this.getPageInfo(pageInfo);
            }else {
            	PageInfo<BriefSendRecord> pageInfo = articleService.searchBriefSendRecord(record, pageNum, pageSize);
            	ret = this.getPageInfo(pageInfo);
            }
            
        }
        else {
            List<BriefSendRecord> records = articleService.findBriefSendRecord(record);
            ret = this.getSingleInfo(records);
        }
        return ret;
    }


    /**
     * 根据ID获得文档信息
     * @param recordId 文档唯一标识
     * @return
     */
    public Map<String, Object> getArticleById(String recordId) {
        recordId = StringUtil.trim(recordId);
        if ("".equals(recordId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Article article = new Article();
                article.setRecordId(recordId);
                List<Article> articles = articleService.find(article);
                if (articles != null || !articles.isEmpty()) {
                    ret = this.getSuccessVo("", articles.get(0));
                }
                else {
                    ret = this.getParamErrorVo();
                }
            } catch (Exception e) {
                e.printStackTrace();
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

	public void deleteArticleById(String recordId,String userId) {
		articleService.deleteArticleById(recordId,userId);
		
	}

	public Map<String, Object> searchApproveXXJLRecord(String userId, String flag, String categoryId, int pageNum, int pageSize) {
		PageInfo<BriefSendRecord> pageInfo = articleService.searchApproveXXJLRecord(userId,Integer.parseInt(flag),categoryId,pageNum,pageSize);
    	ret = this.getPageInfo(pageInfo);
		return ret;
	}
	public void xxjlApprove(String userId, String briefId, String approveType) {
		articleService.updateArtcleUser(briefId,userId,Integer.parseInt(approveType));
	}
}
