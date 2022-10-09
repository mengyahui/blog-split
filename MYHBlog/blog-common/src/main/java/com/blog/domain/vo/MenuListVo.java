package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 孟亚辉
 * @time 2022/10/5 12:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuListVo {

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String label;

    /**
     * 父菜单ID
     */
    private Long parentId;

    List<MenuListVo> children;
}
