package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.domain.ResponseResult;
import com.blog.domain.Role;
import com.blog.domain.RoleMenu;
import com.blog.domain.dto.AddRoleDto;
import com.blog.domain.dto.ChangeRoleStatusDto;
import com.blog.domain.dto.UpdateRoleDto;
import com.blog.domain.vo.PageVo;
import com.blog.domain.vo.RoleVo;
import com.blog.service.RoleMenuService;
import com.blog.service.RoleService;
import com.blog.mapper.RoleMapper;
import com.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MYH
 * @description 针对表【t_role(角色信息表)】的数据库操作Service实现
 * @createDate 2022-10-01 14:39:01
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 如果是管理员，返回集合中只需要有 "admin"
        if (id == 1) {
            List<String> roles = new ArrayList<String>();
            roles.add("admin");
            return roles;
        }

        // 否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult pageRole(Integer pageNum, Integer pageSize, String roleName, String status) {
        // 查询条件
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName);
        queryWrapper.eq(StringUtils.hasText(status), Role::getStatus, status);
        queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        // 封装数据
        List<Role> roleList = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roleList, RoleVo.class);
        PageVo pageVo = new PageVo(roleVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(ChangeRoleStatusDto roleStatusDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId, roleStatusDto.getRoleId());
        Role role = getOne(queryWrapper);
        role.setStatus(roleStatusDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        // 保存角色信息
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);
        // 新建菜单信息到 t_role_menu
        List<RoleMenu> roleMenuList = addRoleDto.getMenuIds()
                .stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        // 更新角色信息
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);
        // 删除角色所对应的菜单信息
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, role.getId());
        roleMenuService.remove(queryWrapper);
        // 添加角色所对应的菜单信息
        List<Long> menuIds = updateRoleDto.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        // 删除角色信息
        removeById(id);
        // 删除角色所对应的菜单信息
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, id);
        roleMenuService.remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roles = list(queryWrapper);
        return ResponseResult.okResult(roles);
    }


}




