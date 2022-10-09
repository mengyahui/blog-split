package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.domain.Article;
import com.blog.domain.ArticleTag;
import com.blog.domain.Category;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddArticleDto;
import com.blog.domain.dto.SelectArticleDto;
import com.blog.domain.vo.*;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;
import com.blog.service.ArticleTagService;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.RedisCache;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author MYH
 * @description 针对表【t_article(文章表)】的数据库操作Service实现
 * @createDate 2022-09-26 21:07:22
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult getHotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        // 1. 必须是已发布文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 2. 按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 3. 最多 10 条数据
        Page<Article> page = new Page<Article>(1, 10);
        page(page, queryWrapper);
        List<Article> records = page.getRecords();
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);
        // 修改返回的文章列表中文章浏览量为缓存中的
        List<HotArticleVo> articleList = hotArticleVos.stream()
                .peek(article -> {
                    Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
                    article.setViewCount(viewCount.longValue());
                })
                .collect(Collectors.toList());
        return ResponseResult.okResult(articleList);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 1. 查询条件：判断 categoryId、已发布文章、置顶文章
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        // 2. 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        List<Article> articles = page.getRecords();
        // 修改返回的文章列表中文章浏览量为缓存中的
        List<Article> articleList = articles.stream()
                .peek(article -> {
                    Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
                    article.setViewCount(viewCount.longValue());
                })
                .collect(Collectors.toList());
        for (Article article : articleList) {
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }

        // 3. 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 1.根据 id 查询文章
        Article article = getById(id);
        // 从 redis 中获取文章浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        // 向返回的数据中，设置浏览量
        article.setViewCount(viewCount.longValue());
        // 2.转换 Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 3.根据 id 查询分类名字
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        // 4.封装响应数据
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新 redis 中对应文章的浏览量
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addArticle(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult pageList(Integer pageNum, Integer pageSize, SelectArticleDto selectArticleDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(selectArticleDto.getTitle()), Article::getTitle, selectArticleDto.getTitle());
        queryWrapper.eq(StringUtils.hasText(selectArticleDto.getSummary()), Article::getSummary, selectArticleDto.getSummary());

        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Article> articleList = page.getRecords();
        long total = page.getTotal();
        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(articleList, ArticleVo.class);
        PageVo pageVo = new PageVo(articleVos, total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult selectArticleById(Long id) {
        Article article = getById(id);
        // 获取文章标签信息
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> list = articleTagService.list(queryWrapper);
        List<Long> tags = list.stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        ArticleSelectVo articleSelectVo = BeanCopyUtils.copyBean(article, ArticleSelectVo.class);
        articleSelectVo.setTags(tags);
        return ResponseResult.okResult(articleSelectVo);
    }

    @Override
    public ResponseResult updateArticle(ArticleSelectVo articleVo) {
        // 修改文章信息
        Article article = BeanCopyUtils.copyBean(articleVo, Article.class);
        updateById(article);
        // 修改文章所对应的标签信息
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, article.getId());

        // 删除原来的文章标签
        articleTagService.remove(queryWrapper);
        // 添加新的文章标签
        List<Long> tags = articleVo.getTags();
        List<ArticleTag> articleTags = tags.stream()
                .map(tag -> new ArticleTag(articleVo.getId(), tag))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}




