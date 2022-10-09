package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.domain.Menu;
import com.blog.domain.ResponseResult;
import com.blog.domain.RoleMenu;
import com.blog.domain.vo.MenuListVo;
import com.blog.domain.vo.MenuVo;
import com.blog.domain.vo.RoleMenuVo;
import com.blog.service.MenuService;
import com.blog.mapper.MenuMapper;
import com.blog.service.RoleMenuService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MYH
 * @description 针对表【t_menu(菜单权限表)】的数据库操作Service实现
 * @createDate 2022-10-01 14:38:46
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员返回所有的权限，否则返回其所具有的权限
        if (id.equals(1L)) {
            LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(Menu::getMenuType, "C", "F");
            lambdaQueryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(lambdaQueryWrapper);
            return menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
        }
        // 否则返回其所具有的权限

        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        // 如果是管理员，返回所有符合的 menu
        if (userId.equals(1L)) {
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 否则查询当前用户所具有的 menu
            menus = menuMapper.selectRouterMenuByUserId(userId);
        }
        // 构建 menuTree
        return buildMenuTree(menus, 0L);
    }

    @Override
    public ResponseResult listMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        queryWrapper.eq(StringUtils.hasText(status), Menu::getStatus, status);
        queryWrapper.orderByAsc(Menu::getId);
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menuList = list(queryWrapper);
        return ResponseResult.okResult(menuList);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        // 获取当前登录用户的 id
        Long userId = SecurityUtils.getUserId();
        // 获取当前用户所具有的所有菜单
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        // 如果是管理员，返回所有符合的 menu
        if (userId.equals(1L)) {
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 否则查询当前用户所具有的 menu
            menus = menuMapper.selectRouterMenuByUserId(userId);
        }
        // 添加 label 属性
        List<Menu> menuList = menus.stream()
                .peek(menu -> menu.setLabel(menu.getMenuName()))
                .collect(Collectors.toList());
        // 构建菜单树
        List<Menu> menuTree = buildMenuTree(menus, 0L);
//        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(menuList, MenuListVo.class);
        return ResponseResult.okResult(menuTree);
    }

    @Override
    public ResponseResult roleMenuTreeSelect(Long id) {
        // 根据角色 roleId 查询菜单 menuId 列表
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, id);
        List<RoleMenu> list = roleMenuService.list(queryWrapper);

        List<Long> menuIds = list.stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
        // 根据菜单 id 列表查询菜单树
        List<Menu> menuList = listByIds(menuIds);
        // 添加 label 属性
        List<Menu> collect = menuList.stream()
                .peek(menu -> menu.setLabel(menu.getMenuName()))
                .collect(Collectors.toList());
        List<Menu> menuTree = buildMenuTree(collect, 0L);

        return ResponseResult.okResult(new RoleMenuVo(menuTree, menuIds));
    }


    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
    }

    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        return menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .peek(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
    }
}




