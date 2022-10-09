package com.blog.service;

import com.blog.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddCategoryDto;
import com.blog.domain.vo.CategoryVo;

/**
* @author MYH
* @description 针对表【t_category(分类表)】的数据库操作Service
* @createDate 2022-09-27 12:22:14
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult pageCategoryList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(AddCategoryDto addCategoryDto);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(CategoryVo categoryVo);

    ResponseResult deleteCategory(Long id);
}
