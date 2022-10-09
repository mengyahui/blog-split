package com.blog.runner;

import com.blog.domain.Article;
import com.blog.mapper.ArticleMapper;
import com.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @author 孟亚辉
 * @time 2022/9/30 17:26
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 从数据库中取出文章列表
        List<Article> articleList = articleMapper.selectList(null);
        // 将文章列表转化为 key 为 文章 id  value 为 文章浏览量的 map 集合
        Map<String, Integer> viewCountMap = articleList.stream()
                .collect(Collectors
                        .toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        // 将文章浏览量的 map 集合存储到 redis 中
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
