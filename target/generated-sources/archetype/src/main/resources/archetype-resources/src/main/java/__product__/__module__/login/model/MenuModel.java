#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.login.model;

import com.github.hbq969.code.dict.model.Pair;
import ${package}.${product}.${module}.login.dao.entity.MenuEntity;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class MenuModel {
    /**
     * 所有菜单
     */
    private PageInfo<MenuEntity> menus;
    /**
     * 一级菜单
     */
    private List<MenuEntity> level1Menus;

    /**
     * 图标列表
     */
    private List<Pair> iconList;
}
