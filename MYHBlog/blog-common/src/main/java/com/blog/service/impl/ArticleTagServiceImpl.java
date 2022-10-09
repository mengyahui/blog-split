package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ArticleTag;
import com.blog.service.ArticleTagService;
import com.blog.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author MYH
* @description 针对表【t_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2022-10-04 14:06:05
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




