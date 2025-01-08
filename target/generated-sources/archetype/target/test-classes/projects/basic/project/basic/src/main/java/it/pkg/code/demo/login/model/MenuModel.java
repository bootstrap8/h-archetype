package it.pkg.code.demo.login.model;

import com.github.hbq969.code.dict.model.Pair;
import it.pkg.code.demo.login.dao.entity.MenuEntity;
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
