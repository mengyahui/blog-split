package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author 孟亚辉
 * @time 2022/9/27 10:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    /**
     * id
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 访问量
     */
    private Long viewCount;
}
