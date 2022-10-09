package com.blog.domain.dto;

/*
 * @author 孟亚辉
 * @time 2022/10/5 20:38
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDto {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;

    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phonenumber;

    private List<Long> roleIds;
}
