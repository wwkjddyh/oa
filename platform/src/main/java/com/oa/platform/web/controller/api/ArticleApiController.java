package com.oa.platform.web.controller.api;

import com.oa.platform.biz.ArticleBiz;
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
            @RequestParam(defaultValue = "1", required = false) String flag) {
        return articleBiz.saveArticle(recordId, categoryId, title, intro, content, tags,source, authorName, sourceSite, flag);
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

}
