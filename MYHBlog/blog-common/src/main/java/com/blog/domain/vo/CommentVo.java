package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/*
 * @author 孟亚辉
 * @time 2022/9/29 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 根评论id
     */
    private Long rootId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 所回复的目标评论的userid
     */
    private Long toCommentUserId;
    private String toCommentUserName;

    /**
     * 回复目标评论id
     */
    private Long toCommentId;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    private Date createTime;

    private String username;
    /**
     * 子评论列表
     */
    private List<CommentVo> children;
}
