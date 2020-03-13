package com.oa.platform.web.controller.api;

import com.oa.platform.biz.CategoryBiz;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 类别
 * @author Feng
 * @date 2019/03/15
 */
@RestController
@RequestMapping("/api/category")
public class CategoryApiController extends BaseController {

    @Autowired
    private CategoryBiz categoryBiz;

    /**
     * 保存类别信息
     * @param recordId 信息唯一标识
     * @param typeId 类别分类唯一标识
     * @param name 名称
     * @param desc 描述
     * @param flag 标志
     * @return
     */
    @PostMapping("saveCategory")
    public Map<String,Object> saveCategory(@RequestParam(defaultValue = "", required = false) String recordId,
                                           @RequestParam String typeId,
                                           @RequestParam String name,
                                           @RequestParam(defaultValue = "", required = false) String desc,
                                           @RequestParam(defaultValue = "1", required = false) String flag) {
        return categoryBiz.saveCategory(recordId, typeId, name, desc, flag);
    }

    /**
     * 保存类别分类信息
     * @param recordId 信息唯一标识
     * @param name 名称
     * @param desc 描述
     * @param flag 标志
     * @return
     */
    @PostMapping("saveCategoryType")
    public Map<String,Object> saveCategoryType(@RequestParam(defaultValue = "", required = false) String recordId,
                                               @RequestParam String name,
                                               @RequestParam(defaultValue = "", required = false) String desc,
                                               @RequestParam(defaultValue = "1", required = false) String flag) {
        return categoryBiz.saveCategoryType(recordId, name, desc, flag);
    }

    /**
     * 检索(类别信息)
     * @param recordId 信息唯一标识
     * @param typeId 类别分类唯一标识
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("search")
    public Map<String,Object> search(@RequestParam(defaultValue = "",required = false) String recordId,
                                     @RequestParam(defaultValue = "",required = false) String typeId,
                                     @RequestParam(defaultValue = "",required = false) String key,
                                     @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
                                     @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return categoryBiz.search(recordId, typeId, key, pageNum, pageSize);
    }

    /**
     * 检索(类别信息)
     * @param recordId 信息唯一标识
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("searchType")
    public Map<String,Object> searchType(@RequestParam(defaultValue = "",required = false) String recordId,
                                         @RequestParam(defaultValue = "",required = false) String key,
                                         @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
                                         @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return categoryBiz.searchType(recordId, key, pageNum, pageSize);
    }

    /**
     * 根据id更新类别信息状态标志
     * @param id
     * @param flag
     * @return
     */
    @PostMapping("updateFlagById")
    public Map<String,Object> updateFlagById(@RequestParam String id, @RequestParam String flag) {
        return categoryBiz.updateFlagById(id, flag);
    }

    /**
     * 根据id更新类别分类信息状态标志
     * @param id
     * @param flag
     * @return
     */
    @PostMapping("updateTypeFlagById")
    public Map<String,Object> updateTypeFlagById(@RequestParam String id, @RequestParam String flag) {
        return categoryBiz.updateTypeFlagById(id, flag);
    }

    /**
     * 所有分类类别信息
     * @return
     */
    @GetMapping("allCategoryTypes")
    public Map<String, Object> allCategoryTypes() {
        return  categoryBiz.allCategoryTypes();
    }

    /**
     * 获得文章分类信息
     * @return
     */
    @GetMapping("allArticleCategories")
    public Map<String, Object> allArticleCategories() {
        return categoryBiz.getCategoriesByTypeName("文章");
    }

    /**
     * 获得消息类别的所有分类
     * @return
     */
    @GetMapping("allMsgCategories")
    public Map<String, Object> allMsgCategories() {
        return categoryBiz.getCategoriesByTypeName("消息");
    }

    /**
     * 根据类别获得消息分类
     * @param typeId 类别序号
     * @return
     */
    @GetMapping("getCategoriesByTypeId")
    public Map<String, Object> getCategoriesByTypeId(@RequestParam String typeId) {
        return categoryBiz.getCategoriesByTypeId(typeId);
    }
}
