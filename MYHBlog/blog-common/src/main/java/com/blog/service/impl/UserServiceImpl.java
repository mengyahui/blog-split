package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.Role;
import com.blog.domain.User;
import com.blog.domain.UserRole;
import com.blog.domain.dto.AddUserDto;
import com.blog.domain.dto.SelectUserDto;
import com.blog.domain.dto.UpdateUserDto;
import com.blog.domain.vo.PageVo;
import com.blog.domain.vo.UserInfoVo;
import com.blog.domain.vo.UserRoleVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import com.blog.service.RoleService;
import com.blog.service.UserRoleService;
import com.blog.service.UserService;
import com.blog.mapper.UserMapper;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MYH
 * @description 针对表【t_user(用户表)】的数据库操作Service实现
 * @createDate 2022-09-27 17:46:08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userInfo() {
        // 1. 获取当前用户 id
        Long userId = SecurityUtils.getUserId();
        // 2. 根据用户 id 查询用户信息
        User user = getById(userId);
        // 3. 封装成 userInfo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        // 对数据进行重复性判断
        if (exitUserName(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (exitNickName(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (exitEmailName(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 对密码进行加密处理
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        // 存入信息进数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult pageUserList(Integer pageNum, Integer pageSize, SelectUserDto selectUserDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(selectUserDto.getPhonenumber()), User::getPhonenumber, selectUserDto.getPhonenumber());
        queryWrapper.like(StringUtils.hasText(selectUserDto.getUserName()), User::getUserName, selectUserDto.getUserName());
        queryWrapper.eq(StringUtils.hasText(selectUserDto.getStatus()), User::getStatus, selectUserDto.getStatus());
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
    }

    @Override
    public ResponseResult addUser(AddUserDto addUserDto) {
        // 添加用户信息
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        // 添加用户所对应的角色信息
        List<Long> roleIds = addUserDto.getRoleIds();
        List<UserRole> userRoles = roleIds.stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getStatus, "1").eq(User::getId, id);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserById(Long id) {
        // 根据用户id 查询 其所具有的角色id 列表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        // 查询所具有的角色
        List<Role> roles = roleService.list();
        // 查询用户信息
        User user = getById(id);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // 封装数据
        return ResponseResult.okResult(new UserRoleVo(roleIds, roles, userInfoVo));
    }

    @Override
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        // 更新用户信息
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        updateById(user);
        // 更新用户角色信息
        // 1. 删除该用户原来的角色信息
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, user.getId());
        userRoleService.remove(queryWrapper);
        // 2. 添加修修改后的角色信息
        List<Long> roleIds = updateUserDto.getRoleIds();
        List<UserRole> userRoleList = roleIds.stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    private boolean exitEmailName(String email) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail, email);
        return count(lambdaQueryWrapper) > 0;
    }

    private boolean exitNickName(String nickName) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getNickName, nickName);
        return count(lambdaQueryWrapper) > 0;
    }

    private boolean exitUserName(String userName) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, userName);
        return count(lambdaQueryWrapper) > 0;
    }
}




