package com.blog.domain.vo;

import com.blog.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @author 孟亚辉
 * @time 2022/10/5 21:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleVo {

    private List<Long> roleIds;
    private List<Role> roles;
    private UserInfoVo user;
}
