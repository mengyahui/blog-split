package com.blog.job;

import com.blog.domain.Article;
import com.blog.service.ArticleService;
import com.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 孟亚辉
 * @time 2022/9/30 17:52
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 10/10 * * * ? ")
    public void updateViewCount(){

        // 获取 redis 中的文章浏览量数据
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        // 更新到数据库中
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);

    }
}
