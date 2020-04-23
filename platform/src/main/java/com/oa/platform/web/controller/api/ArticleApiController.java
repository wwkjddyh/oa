package com.oa.platform.web.controller.api;

import com.oa.platform.biz.ArticleBiz;
import com.oa.platform.common.Constants;
import com.oa.platform.common.ResultVo;
import com.oa.platform.common.StatusCode;
import com.oa.platform.util.StringUtil;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 文章
 * @author Feng
 * @date 2020/03/16
 */
@RestController
@RequestMapping("/api/article")
public class ArticleApiController extends BaseController {

    @Autowired
    private ArticleBiz articleBiz;

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
     * @param sendType 发送类型（"1": 简报）
     * @param receiverIds 简报接收者ID组
     * @return
     */
    @PostMapping("save")
    public Map<String,Object> saveArticle(
            @RequestParam(defaultValue = "", required = false) String recordId,
            @RequestParam String categoryId,
            @RequestParam String title,
            @RequestParam(defaultValue = "", required = false) String intro,
            @RequestParam String content,
            @RequestParam(defaultValue = "", required = false) String tags,
            @RequestParam(defaultValue = "", required = false) String source,
            @RequestParam(defaultValue = "", required = false) String authorName,
            @RequestParam(defaultValue = "", required = false) String sourceSite,
            @RequestParam(defaultValue = "1", required = false) String flag,
            @RequestParam(defaultValue = "", required = false) String sendType,
            String[] receiverIds) {
        return articleBiz.saveArticle(recordId, categoryId, title, intro, content, tags,source, authorName, sourceSite,
                flag, sendType, receiverIds);
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
    @GetMapping("search")
    public Map<String,Object> search(@RequestParam(defaultValue = "", required = false) String recordId,
                                     @RequestParam(defaultValue = "", required = false) String categoryId,
                                     @RequestParam(defaultValue = "", required = false) String creatorId,
                                     @RequestParam(defaultValue = "", required = false) String updatorId,
                                     @RequestParam(defaultValue = "", required = false) String key,
                                     @RequestParam(defaultValue = PAGE_NUM_STR, required = false) int pageNum,
                                     @RequestParam(defaultValue = PAGE_SIZE_STR, required = false) int pageSize) {
        return articleBiz.search(recordId, categoryId, creatorId,updatorId, key, pageNum, pageSize);
    }

    /**
     * 根据Id更新文章状态
     * @param id 信息唯一标识
     * @param flag 信息状态标志('0',删除;'1',正常)
     * @return
     */
    @PostMapping("updateArticleFlagById")
    public Map<String,Object> updateArticleFlagById(@RequestParam String id, @RequestParam String flag) {
        return articleBiz.updateArticleFlagById(id, flag);
    }

    /**
     * 根据简报ID查询接收者ID列表
     * @param briefId 简报ID
     * @return
     */
    @GetMapping("getReceiverIdsByBriefId")
    public Map<String, Object> getReceiverIdsByBriefId(@RequestParam String briefId) {
        return articleBiz.getReceiverIdsByBriefId(briefId);
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
    @GetMapping("searchBriefSendRecord")
    public Map<String, Object> searchBriefSendRecord(
            @RequestParam(defaultValue = "", required = false) String id,
            @RequestParam(defaultValue = "", required = false) String briefId,
            @RequestParam(defaultValue = "", required = false) String senderId,
            @RequestParam(defaultValue = "", required = false) String receiverId,
            Integer status,
            Integer recordFlag,
            @RequestParam(defaultValue = "", required = false) String sendTime,
            @RequestParam(defaultValue = "", required = false) String viewTime,
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false)  int pageSize,
            @RequestParam(defaultValue = "53c34dec-7447-4bbc-9ff3-af0f0686b07f", required = false) String categoryId) {
        return articleBiz.searchBriefSendRecord(id, briefId, senderId, receiverId,
                status, recordFlag, sendTime, viewTime, key, pageNum, pageSize,categoryId);
    }

    /**
     * 获得当前用户接收到的简报信息
     * @param id 唯一标识
     * @param briefId 简报ID
     * @param senderId 发送者ID
     * @param status 状态
     * @param sendTime 发送时间
     * @param viewTime 浏览时间
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("getCurrUserReceiverBriefRecord")
    public Map<String, Object> getCurrUserReceiverBriefRecord(
            @RequestParam(defaultValue = "", required = false) String id,
            @RequestParam(defaultValue = "", required = false) String briefId,
            @RequestParam(defaultValue = "", required = false) String senderId,
            Integer status,
            @RequestParam(defaultValue = "", required = false) String sendTime,
            @RequestParam(defaultValue = "", required = false) String viewTime,
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false)  int pageSize,
            @RequestParam(defaultValue = "", required = false) String flag,
            @RequestParam(defaultValue = "53c34dec-7447-4bbc-9ff3-af0f0686b07f", required = false) String categoryId) {
        String userId = this.getUserIdOfSecurity();
        if ("".equals(userId)) {
            return StringUtil.getResultVo(StatusCode.UNAUTHORIZED, "请登录", "");
        }
        if(flag != null && "0".equals(flag)) {
        	return articleBiz.searchBriefSendRecord(id, briefId, userId, null,
                    status, Constants.INT_NORMAL, sendTime, viewTime, key, pageNum, pageSize,categoryId);
        }else if("2".equals(flag)) {
        	//待审批
        	return articleBiz.searchApproveXXJLRecord(userId,flag,categoryId,pageNum,pageSize);
        }else if("3".equals(flag)) {
        	//审批通过
        	return articleBiz.searchApproveXXJLRecord(userId,flag,categoryId,pageNum,pageSize);
        }else if("4".equals(flag)) {
        	//审批未通过
        	return articleBiz.searchApproveXXJLRecord(userId,flag,categoryId,pageNum,pageSize);
        }
        else {
        	return articleBiz.searchBriefSendRecord(id, briefId, senderId, userId,
                    status, Constants.INT_NORMAL, sendTime, viewTime, key, pageNum, pageSize,categoryId);
        }
        
    }


    /**
     * 根据ID获得文档信息
     * @param id 文档唯一标识
     * @return
     */
    @GetMapping("get")
    public Map<String, Object> getArticleById(@RequestParam String id) {
        return articleBiz.getArticleById(id);
    }
    /**
     * 用户删除已发简报
     * @param recordId
     * @return
     */
    @PostMapping("deleteArticleById")
    public ResultVo deleteArticleById(String recordId) {
    	String userId = this.getUserIdOfSecurity();
    	articleBiz.deleteArticleById(recordId,userId);
    	return getSuccessResultVo(null);
    }
    public ResultVo xxjlApprove(String briefId,String approveType) {
    	String userId = this.getUserIdOfSecurity();
    	articleBiz.xxjlApprove(userId,briefId,approveType);
    	return getSuccessResultVo(null);
    }
}
