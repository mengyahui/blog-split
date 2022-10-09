package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddArticleDto;
import com.blog.domain.dto.SelectArticleDto;
import com.blog.domain.vo.ArticleSelectVo;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * @author 孟亚辉
 * @time 2022/10/4 13:54
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto article){
        return articleService.addArticle(article);
    }

    @GetMapping("/list")
    public ResponseResult pageList(Integer pageNum, Integer pageSize, SelectArticleDto selectArticleDto){
        return articleService.pageList(pageNum, pageSize, selectArticleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult selectArticleById(@PathVariable("id") Long id){
        return articleService.selectArticleById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleSelectVo article){
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long  id){
        return articleService.deleteArticle(id);
    }
}
