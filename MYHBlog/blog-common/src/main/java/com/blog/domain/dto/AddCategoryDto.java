package com.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author 孟亚辉
 * @time 2022/10/5 22:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryDto {

    private String name;
    private String description;
    private String status;
}
