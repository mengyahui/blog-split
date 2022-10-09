package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.UserRole;
import com.blog.service.UserRoleService;
import com.blog.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author MYH
* @description 针对表【t_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2022-10-05 20:42:45
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




