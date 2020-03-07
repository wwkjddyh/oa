package com.oa.platform.service;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.Category;
import com.oa.platform.entity.CategoryType;

import java.util.List;

/**
 * 类别信息
 * @author Feng
 * @date 2019/03/14
 */
public interface CategoryService extends BaseService<Category,String> {

    /**
     * 保存类别分类信息
     * @param categoryType 类别分类对象
     */
    void saveCategoryType(CategoryType categoryType);

    /**
     * 更新类别分类信息
     * @param categoryType 类别分类对象
     */
    void updateCategoryType(CategoryType categoryType);

    /**
     * 删除类别分类信息
     * @param categoryType 类别分类对象
     */
    void deleteCategoryType(CategoryType categoryType);

    /**
     * 查询类别分类信息
     * @param categoryType 类别分类对象
     * @return
     */
    List<CategoryType> findCategoryType(CategoryType categoryType);

    /**
     * 分页查询客户信息
     * @param categoryType 类别分类对象
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    PageInfo<CategoryType> searchCategoryType(CategoryType categoryType, int pageNum, int pageSize);
}
