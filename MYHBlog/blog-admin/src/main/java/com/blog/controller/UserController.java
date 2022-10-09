package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddUserDto;
import com.blog.domain.dto.SelectUserDto;
import com.blog.domain.dto.UpdateUserDto;
import com.blog.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 孟亚辉
 * @time 2022/10/5 20:19
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult pageUserList(Integer pageNum, Integer pageSize, SelectUserDto selectUserDto){
        return userService.pageUserList(pageNum, pageSize, selectUserDto);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto){
        return userService.addUser(addUserDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
    }

}
