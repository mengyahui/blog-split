package com.blog.service;

import com.blog.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;

import java.util.List;

/**
* @author MYH
* @description 针对表【t_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-10-01 14:38:47
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult listMenu(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);

    ResponseResult treeSelect();

    ResponseResult roleMenuTreeSelect(Long id);
}
