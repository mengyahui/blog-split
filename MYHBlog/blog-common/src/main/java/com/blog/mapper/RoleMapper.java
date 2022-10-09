package com.blog.mapper;

import com.blog.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author MYH
* @description 针对表【t_role(角色信息表)】的数据库操作Mapper
* @createDate 2022-10-01 14:39:01
* @Entity com.blog.domain.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}




