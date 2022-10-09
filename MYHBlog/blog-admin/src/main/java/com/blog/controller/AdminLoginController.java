package com.blog.controller;

import com.blog.domain.LoginUser;
import com.blog.domain.Menu;
import com.blog.domain.ResponseResult;
import com.blog.domain.User;
import com.blog.domain.vo.AdminUserInfoVo;
import com.blog.domain.vo.RoutersVo;
import com.blog.domain.vo.UserInfoVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import com.blog.service.AdminLoginService;
import com.blog.service.BlogLoginService;
import com.blog.service.MenuService;
import com.blog.service.RoleService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.RedisCache;
import com.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @author 孟亚辉
 * @time 2022/9/30 21:50
 */
@RestController
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisCache redisCache;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }


    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 根据用户id 查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        // 根据用户id 查询用户角色信息
        List<String> roles = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        // 获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // 封装数据
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roles, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }


    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        // 获取当前登录的用户
        Long userId = SecurityUtils.getUserId();
        // 查询 menu，结果是 tree 的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        // 封装数据
        return ResponseResult.okResult(new RoutersVo((menus)));

    }

    @PostMapping("/user/logout")
    public ResponseResult logout() {
        // 获取当前登录的用户 id
        Long userId = SecurityUtils.getUserId();
        // 删除 redis 中对应的值
        redisCache.deleteObject("login" + userId);
        return ResponseResult.okResult();
    }
}
