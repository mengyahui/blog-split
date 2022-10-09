package com.blog.service;

import com.blog.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;

/**
* @author MYH
* @description 针对表【t_comment(评论表)】的数据库操作Service
* @createDate 2022-09-29 14:51:29
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
