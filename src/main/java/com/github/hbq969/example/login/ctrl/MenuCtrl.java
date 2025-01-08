package com.github.hbq969.example.login.ctrl;

import com.github.hbq969.code.common.restful.ReturnMessage;
import com.github.hbq969.code.dict.service.api.impl.MapDictHelperImpl;
import com.github.hbq969.example.login.dao.entity.MenuEntity;
import com.github.hbq969.example.login.model.MenuModel;
import com.github.hbq969.example.login.service.LoginService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(prefix = "login",name = "enabled",havingValue = "true")
@RestController("${artifactId}-web-MenuCtrl")
@RequestMapping(path = "/${module}-ui/menus")
@Slf4j
@Api(tags = "配置中心-菜单管理")
public class MenuCtrl {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MapDictHelperImpl dict;

    @ApiOperation("查询菜单信息")
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<MenuModel> queryMenuList(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                  @RequestBody MenuEntity q) {
        MenuModel mm = new MenuModel();
        PageInfo<MenuEntity> pg = loginService.queryMenuList(pageNum, pageSize, q);
        mm.setMenus(pg);
        pg = loginService.queryMenuList(-1, -1, new MenuEntity());
        List<MenuEntity> level1Menus = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pg.getList())) {
            for (MenuEntity me : pg.getList()) {
                if (me.getMenuLevel() != null && me.getMenuLevel().intValue() == 1) {
                    level1Menus.add(me);
                }
            }
        }
        mm.setLevel1Menus(level1Menus);
        mm.setIconList(dict.queryPairList("menu,icon"));
        return ReturnMessage.success(mm);
    }

    @ApiOperation("查询菜单信息")
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMessage<MenuModel> queryMenuList() {
        MenuModel mm = new MenuModel();
        PageInfo<MenuEntity> pg = loginService.queryMenuList(-1, -1, new MenuEntity());
        mm.setMenus(pg);
        List<MenuEntity> level1Menus = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pg.getList())) {
            for (MenuEntity me : pg.getList()) {
                if (me.getMenuLevel() != null && me.getMenuLevel().intValue() == 1) {
                    level1Menus.add(me);
                }
            }
        }
        mm.setLevel1Menus(level1Menus);
        mm.setIconList(dict.queryPairList("menu,icon"));
        return ReturnMessage.success(mm);
    }

    @ApiOperation("新增菜单")
    @RequestMapping(path = "/menu", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<?> saveMenu(@RequestBody MenuEntity menu) {
        loginService.saveMenuEntity(menu);
        return ReturnMessage.success("保存成功");
    }

    @ApiOperation("修改菜单")
    @RequestMapping(path = "/menu", method = RequestMethod.PUT)
    @ResponseBody
    public ReturnMessage<?> updateMenu(@RequestBody MenuEntity menu) {
        loginService.updateMenuEntity(menu);
        return ReturnMessage.success("修改成功");
    }

    @ApiOperation("删除菜单")
    @RequestMapping(path = "/menu", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnMessage<?> deleteMenu(@RequestParam(name = "id") Long id) {
        loginService.deleteMenuEntity(id);
        return ReturnMessage.success("删除成功");
    }
}
