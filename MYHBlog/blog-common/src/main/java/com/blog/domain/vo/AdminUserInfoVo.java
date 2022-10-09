package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @author 孟亚辉
 * @time 2022/10/1 14:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoVo {

    private List<String> permissions;
    private List<String> roles;
    private UserInfoVo user;


}
