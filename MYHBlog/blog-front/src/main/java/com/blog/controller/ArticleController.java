package com.blog.controller;

/**
 * @author 孟亚辉
 * @time 2022/9/26 21:08
 */

import com.blog.domain.ResponseResult;
import com.blog.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 查询热门文章，封装数据
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {

        return articleService.getHotArticleList();
    }

    // 分页查询文章列表
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult articleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        return articleService.updateViewCount(id);
    }
}
