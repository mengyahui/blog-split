package com.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @author 孟亚辉
 * @time 2022/10/5 17:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleDto {
    private Long id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色权限字符串
     */
    private String roleKey;
    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    private List<Long> menuIds;
}
