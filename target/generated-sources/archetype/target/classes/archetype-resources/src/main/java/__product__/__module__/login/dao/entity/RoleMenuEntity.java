#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.login.dao.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : hbq969@gmail.com
 * @description : 校色菜单关系
 * @createTime : 2025/1/2 11:20
 */
@Data
public class RoleMenuEntity {
    private String app;
    private RoleEntity role;
    private List<MenuEntity> menus;

    public void addMenu(MenuEntity menuEntity) {
        if (menus == null) {
            menus = new ArrayList<>();
        }
        menus.add(menuEntity);
    }
}
