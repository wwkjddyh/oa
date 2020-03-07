package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Category;
import com.oa.platform.entity.CategoryType;
import com.oa.platform.service.CategoryService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类别业务处理
 * @author Feng
 * @date 2019/03/14
 */
@Component
public class CategoryBiz extends BaseBiz {

    @Autowired
    private CategoryService categoryService;

    /**
     * 保存类别信息
     * @param recordId 信息唯一标识
     * @param typeId 类别分类唯一标识
     * @param name 名称
     * @param desc 描述
     * @param flag 标志
     * @return
     */
    public Map<String,Object> saveCategory(String recordId, String typeId, String name, String desc, String flag) {
        recordId = StringUtil.trim(recordId);
        typeId = StringUtil.trim(typeId);
        name = StringUtil.trim(name);
        desc = StringUtil.trim(desc);
        flag = StringUtil.trim(flag, Constants.INT_NORMAL + "");
        if("".equals(typeId) || "".equals(name)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                boolean isEdit = isEdit(recordId);

                Category validCategory = new Category();
                validCategory.setName(name);
                validCategory.setTypeId(typeId);
                validCategory.setFlag(Constants.INT_NORMAL);
                List<Category> entries = categoryService.find(validCategory);
                boolean isRepeat = false;
                if(entries != null && !entries.isEmpty()) {
                    if(!isEdit) {   //"新增"操作，只要有相同标题则表示重复
                        isRepeat = true;
                    }
                    else {  //"编辑"操作，若不同的信息唯一标识有相同的标题则表示重复
                        final String finalRecordId = recordId;
                        isRepeat = entries.parallelStream().anyMatch(e -> !e.getRecordId().equals(finalRecordId));
                    }
                }

                if(isRepeat) {
                    ret = this.getParamRepeatErrorVo("名称");
                }
                else {
                    Category category = new Category();
                    category.setTypeId(typeId);
                    category.setName(name);
                    category.setDesc(desc);
                    category.setFlag(Integer.parseInt(flag));

                    if (isEdit) {
                        category.setRecordId(recordId);
                        categoryService.update(category);
                    } else {
                        category.setRecordId(StringUtil.getRandomUUID());
                        category.setRecordTime(DateUtil.currDateFormat(null));
                        categoryService.save(category);
                    }
                    ret = this.getSuccessVo("", "");
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
     * 保存类别分类信息
     * @param recordId 信息唯一标识
     * @param name 名称
     * @param desc 描述
     * @param flag 标志
     * @return
     */
    public Map<String,Object> saveCategoryType(String recordId, String name, String desc, String flag) {
        recordId = StringUtil.trim(recordId);
        name = StringUtil.trim(name);
        desc = StringUtil.trim(desc);
        flag = StringUtil.trim(flag, Constants.INT_NORMAL + "");
        if("".equals(name)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                boolean isEdit = isEdit(recordId);

                CategoryType validEntry = new CategoryType();
                validEntry.setName(name);
                validEntry.setFlag(Constants.INT_NORMAL);
                List<CategoryType> entries = categoryService.findCategoryType(validEntry);
                boolean isRepeat = false;
                if(entries != null && !entries.isEmpty()) {
                    if(!isEdit) {
                        isRepeat = true;
                    }
                    else {
                        final String finalRecordId = recordId;
                        isRepeat = entries.parallelStream().anyMatch(e -> !e.getRecordId().equals(finalRecordId));
                    }
                }

                if(isRepeat) {
                    ret = this.getParamRepeatErrorVo("名称");
                }
                else {
                    CategoryType categoryType = new CategoryType();
                    categoryType.setName(name);
                    categoryType.setDesc(desc);
                    categoryType.setFlag(Integer.parseInt(flag));
                    if (isEdit) {
                        categoryType.setRecordId(recordId);
                        categoryService.updateCategoryType(categoryType);
                    } else {
                        categoryType.setRecordId(StringUtil.getRandomUUID());
                        categoryType.setRecordTime(DateUtil.currDateFormat(null));
                        categoryService.saveCategoryType(categoryType);
                    }
                    ret = this.getSuccessVo("", "");
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
     * 检索(类别信息)
     * @param recordId 信息唯一标识
     * @param typeId 类别分类唯一标识
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String,Object> search(String recordId, String typeId, String key, int pageNum,int pageSize) {
        try {
            recordId = StringUtil.trim(recordId);
            Category category = new Category();
            category.setRecordId(recordId);
            category.setFlag(Constants.INT_NORMAL);
            if(!"".equals(recordId)) {
                List<Category> categories = categoryService.find(category);
                ret = this.getSingleInfo(categories);
            }
            else {
                category.setTypeId(StringUtil.trim(typeId));
                category.setKey(StringUtil.trim(key));
                PageInfo<Category> pageInfo = categoryService.search(category, pageNum, pageSize);
                ret = this.getPageInfo(pageInfo);
            }
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 检索(类别信息)
     * @param recordId 信息唯一标识
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String,Object> searchType(String recordId, String key, int pageNum, int pageSize) {
        try {
            recordId = StringUtil.trim(recordId);
            CategoryType cType = new CategoryType();
            cType.setRecordId(recordId);
            cType.setFlag(Constants.INT_NORMAL);
            if(!"".equals(recordId)) {
                List<CategoryType> types = categoryService.findCategoryType(cType);
                ret = this.getSingleInfo(types);
            }
            else {
                cType.setKey(StringUtil.trim(key));
                PageInfo<CategoryType> pageInfo = categoryService.searchCategoryType(cType, pageNum, pageSize);
                ret = this.getPageInfo(pageInfo);
            }
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 根据id更新类别信息状态标志
     * @param id
     * @param flag
     * @return
     */
    public Map<String,Object> updateFlagById(String id, String flag) {
        id = StringUtil.trim(id);
        flag = StringUtil.trim(flag);
        if("".equals(id) || "".equals(flag) || !StringUtil.isNumber(flag)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Category category = new Category();
                category.setFlag(Integer.parseInt(flag));
                category.setRecordId(id);
                categoryService.update(category);
                ret = this.getSuccessVo("", "");
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据id更新类别分类信息状态标志
     * @param id
     * @param flag
     * @return
     */
    public Map<String,Object> updateTypeFlagById(String id, String flag) {
        id = StringUtil.trim(id);
        flag = StringUtil.trim(flag);
        if("".equals(id) || "".equals(flag) || !StringUtil.isNumber(flag)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                CategoryType categoryType = new CategoryType();
                categoryType.setFlag(Integer.parseInt(flag));
                categoryType.setRecordId(id);
                categoryService.updateCategoryType(categoryType);
                ret = this.getSuccessVo("", "");
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 所有分类类别信息
     * @return
     */
    public Map<String, Object> allCategoryTypes() {
        try {
            CategoryType categoryType = new CategoryType();
            categoryType.setFlag(Constants.INT_NORMAL);
            List<CategoryType> categoryTypes = categoryService.findCategoryType(categoryType);
            ret = this.getSuccessVo("", categoryTypes);
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 根据分类名称获得分类信息
     * @param typeName 类型分类名称(若为null或""，则返回空列表)
     * @return
     */
    public Map<String, Object> getArticleCategories(String typeName) {
        try {
            List<Category> categories = new ArrayList<>(0);
            typeName = StringUtil.trim(typeName);
            if(!"".equals(typeName)) {
                Category category = new Category();
                category.setFlag(Constants.INT_NORMAL);
                category.setTypeName(typeName);
                categories = categoryService.find(category);
            }
            ret = this.getSuccessVo("", categories);
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }
}
