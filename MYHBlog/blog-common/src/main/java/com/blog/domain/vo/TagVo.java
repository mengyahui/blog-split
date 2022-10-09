package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author 孟亚辉
 * @time 2022/10/4 12:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVo {

    private Long id;
    private String name;
    private String remark;
}
