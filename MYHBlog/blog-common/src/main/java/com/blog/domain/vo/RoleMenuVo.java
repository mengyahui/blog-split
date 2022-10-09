package com.blog.domain.vo;

import com.blog.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @author 孟亚辉
 * @time 2022/10/5 19:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuVo {
    private List<Menu> menus;
    private List<Long> checkedKeys;
}
