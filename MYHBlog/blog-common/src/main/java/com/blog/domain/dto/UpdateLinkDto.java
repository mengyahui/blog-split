package com.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author 孟亚辉
 * @time 2022/10/5 23:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLinkDto {
    private Long id;

    private String name;

    private String logo;

    private String description;

    /**
     * 网站地址
     */
    private String address;

    private String status;

}
