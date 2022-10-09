package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.domain.Article;
import com.blog.domain.Category;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddCategoryDto;
import com.blog.domain.vo.CategoryVo;
import com.blog.domain.vo.PageVo;
import com.blog.service.ArticleService;
import com.blog.service.CategoryService;
import com.blog.mapper.CategoryMapper;
import com.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MYH
 * @description 针对表【t_category(分类表)】的数据库操作Service实现
 * @createDate 2022-09-27 12:22:14
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        // 1. 查询文章表，状态为已发布的文章
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleQueryWrapper);
        // 2. 获取文章的分类 id，去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        // 3. 查询分类表，封装 vo
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(article -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(article.getStatus()))
                .collect(Collectors.toList());

        List<CategoryVo> categoryVoList = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVoList);
    }

    @Override
    public ResponseResult listAllCategory() {
        List<Category> categoryList = list();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult pageCategoryList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status), Category::getStatus, status);
        queryWrapper.like(StringUtils.hasText(name), Category::getName, name);
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Category> categoryList = page.getRecords();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(new PageVo(categoryVos, page.getTotal()));
    }

    @Override
    public ResponseResult addCategory(AddCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryVo categoryVo) {
        Category category = BeanCopyUtils.copyBean(categoryVo, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Category::getStatus, "1").eq(Category::getId, id);
        update(updateWrapper);
        return ResponseResult.okResult();
    }


}




