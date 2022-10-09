package com.blog.controller;

/*
 * @author 孟亚辉
 * @time 2022/9/29 15:00
 */

import com.blog.annotation.SystemLog;
import com.blog.constants.SystemConstants;
import com.blog.domain.Comment;
import com.blog.domain.ResponseResult;
import com.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "获取评论列表")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @PostMapping
    @SystemLog(businessName = "添加评论")
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友情链接评论列表",notes = "分页获取友情链接评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页页码数"),
            @ApiImplicitParam(name = "pageSize",value = "每页数据个数")
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }
}
