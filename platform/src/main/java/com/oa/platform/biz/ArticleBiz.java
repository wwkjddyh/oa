package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Article;
import com.oa.platform.service.ArticleService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
     * @return
     */
    public Map<String,Object> saveArticle(String recordId, String categoryId, String title, String intro,
                                          String content, String tags, String source, String authorName,
                                          String sourceSite, String flag) {
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
                   ret = this.getParamRepeatErrorVo("标题");
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
                       article.setRecordId(recordId);
                       article.setUpdatorId(userId);
                       article.setUpdateTime(dateStr);
                       articleService.update(article);
                   }
                   else {
                       article.setRecordId(StringUtil.getRandomUUID());
                       article.setCreatorId(userId);
                       article.setRecordTime(dateStr);
                       articleService.save(article);
                   }
                   ret = this.getSuccessVo("","");
               }
           }
           catch(Exception e) {
               loggerError(ThreadUtil.getCurrentFullMethodName(), e);
               ret = this.getErrorVo();
           }
        }
        return ret;
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
}
