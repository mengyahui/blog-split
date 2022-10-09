package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.dto.AddRoleDto;
import com.blog.domain.dto.ChangeRoleStatusDto;
import com.blog.domain.dto.UpdateRoleDto;

import java.util.List;

/**
* @author MYH
* @description 针对表【t_role(角色信息表)】的数据库操作Service
* @createDate 2022-10-01 14:39:01
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult pageRole(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(ChangeRoleStatusDto roleStatusDto);

    ResponseResult addRole(AddRoleDto addRoleDto);

    ResponseResult getRoleById(Long id);

    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllRole();
}
