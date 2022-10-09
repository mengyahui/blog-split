package com.blog.service;

import com.blog.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddArticleDto;
import com.blog.domain.dto.SelectArticleDto;
import com.blog.domain.vo.ArticleSelectVo;

/**
* @author MYH
* @description 针对表【t_article(文章表)】的数据库操作Service
* @createDate 2022-09-26 21:07:22
*/
public interface ArticleService extends IService<Article> {

    ResponseResult getHotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto article);

    ResponseResult pageList(Integer pageNum, Integer pageSize, SelectArticleDto selectArticleDto);

    ResponseResult selectArticleById(Long id);

    ResponseResult updateArticle(ArticleSelectVo article);

    ResponseResult deleteArticle(Long id);
}
