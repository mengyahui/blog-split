package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.dto.AddUserDto;
import com.blog.domain.dto.SelectUserDto;
import com.blog.domain.dto.UpdateUserDto;

/**
* @author MYH
* @description 针对表【t_user(用户表)】的数据库操作Service
* @createDate 2022-09-27 17:46:08
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult pageUserList(Integer pageNum, Integer pageSize, SelectUserDto selectUserDto);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserById(Long id);

    ResponseResult updateUser(UpdateUserDto updateUserDto);
}
