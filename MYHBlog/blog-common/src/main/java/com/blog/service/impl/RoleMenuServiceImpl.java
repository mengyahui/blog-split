package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.RoleMenu;
import com.blog.service.RoleMenuService;
import com.blog.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author MYH
* @description 针对表【t_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2022-10-05 17:01:53
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




