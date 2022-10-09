package com.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author 孟亚辉
 * @time 2022/10/4 22:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectArticleDto {

    private  String title;
    private String summary;
}
