package com.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author 孟亚辉
 * @time 2022/10/5 11:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleStatusDto {

    private Long roleId;
    private String status;
}
