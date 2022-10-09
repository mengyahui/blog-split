package com.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author 孟亚辉
 * @time 2022/10/5 20:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectUserDto {
    private String userName;
    private String phonenumber;
    private String status;
}
