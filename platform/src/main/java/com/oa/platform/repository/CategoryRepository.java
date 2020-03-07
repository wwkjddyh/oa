package com.oa.platform.repository;

import com.oa.platform.entity.Category;
import com.oa.platform.entity.CategoryType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类别信息
 * @author Feng
 * @date 2019/03/14
 */
@Repository
public interface CategoryRepository extends BaseRepository<Category,String> {

    /**
     * 插入类别分类信息
     * @param categoryType 类别分类对象
     */
    void insertCategoryType(CategoryType categoryType);

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
}
