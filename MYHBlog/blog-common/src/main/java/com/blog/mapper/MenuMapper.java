package com.blog.mapper;

import com.blog.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author MYH
* @description 针对表【t_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2022-10-01 14:38:46
* @Entity com.blog.domain.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuByUserId(Long userId);
}




