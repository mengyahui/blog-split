package com.blog.controller;

/*
 * @author 孟亚辉
 * @time 2022/9/27 12:23
 */

import com.blog.annotation.SystemLog;
import com.blog.domain.ResponseResult;
import com.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "获取分类列表")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
