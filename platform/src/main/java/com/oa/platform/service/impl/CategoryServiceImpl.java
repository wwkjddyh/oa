package com.oa.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.Category;
import com.oa.platform.entity.CategoryType;
import com.oa.platform.repository.CategoryRepository;
import com.oa.platform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类别信息
 * @author Feng
 * @date 2019/03/14
 */
@Service
public class CategoryServiceImpl extends AbstractBaseService<Category,String> implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl() {
        super(Category.class);
    }

    @Override
    public void saveCategoryType(CategoryType categoryType) {
        categoryRepository.insertCategoryType(categoryType);
    }

    @Override
    public void updateCategoryType(CategoryType categoryType) {
        categoryRepository.updateCategoryType(categoryType);
    }

    @Override
    public void deleteCategoryType(CategoryType categoryType) {
        categoryRepository.deleteCategoryType(categoryType);
    }

    @Override
    public List<CategoryType> findCategoryType(CategoryType categoryType) {
        return categoryRepository.findCategoryType(categoryType);
    }

    @Override
    public PageInfo<CategoryType> searchCategoryType(CategoryType categoryType, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(categoryRepository.findCategoryType(categoryType));
    }
}
