package com.github.hbq969.example.login.ctrl;

import com.github.hbq969.code.common.restful.ReturnMessage;
import com.github.hbq969.example.login.dao.entity.MenuEntity;
import com.github.hbq969.example.login.dao.entity.RoleEntity;
import com.github.hbq969.example.login.dao.entity.RoleMenuEntity;
import com.github.hbq969.example.login.model.RoleMenu;
import com.github.hbq969.example.login.service.LoginService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : hbq969@gmail.com
 * @description : 角色管理
 * @createTime : 2025/1/2 10:57
 */
@ConditionalOnProperty(prefix = "login",name = "enabled",havingValue = "true")
@RestController("${artifactId}-web-RoleCtrl")
@RequestMapping(path = "/${module}-ui/roles")
@Slf4j
@Api(tags = "配置中心-角色管理")
public class RoleCtrl {

    @Autowired
    private LoginService loginService;

    @ApiOperation("分页查询角色列表")
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<PageInfo<RoleEntity>> queryRoleList(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, @RequestBody RoleEntity q) {
        return ReturnMessage.success(loginService.queryRoleList(pageNum, pageSize, q));
    }

    @ApiOperation("新增角色")
    @RequestMapping(path = "/role", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<String> saveRole(@RequestBody RoleEntity role) {
        loginService.saveRoleEntity(role);
        return ReturnMessage.success("保存成功");
    }

    @ApiOperation("修改角色")
    @RequestMapping(path = "/role", method = RequestMethod.PUT)
    @ResponseBody
    public ReturnMessage<?> updateRole(@RequestBody RoleEntity role) {
        loginService.updateRoleEntity(role);
        return ReturnMessage.success("修改成功");
    }

    @ApiOperation("删除角色")
    @RequestMapping(path = "/role", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnMessage<?> deleteRole(@RequestParam(name = "id") Long id) {
        loginService.deleteRoleEntity(id);
        return ReturnMessage.success("删除成功");
    }

    @ApiOperation("保存角色关联菜单")
    @RequestMapping(path = "/role/menus", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<?> saveRoleMenus(@RequestBody RoleMenuEntity rme) {
        loginService.updateRoleMenus(rme);
        return ReturnMessage.success("保存成功");
    }

    @ApiOperation("查询角色菜单配置")
    @RequestMapping(path = "/role/menus", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMessage<RoleMenu> queryRoleMenus(@RequestParam(name = "id") Long id) {
        RoleMenu roleMenu = new RoleMenu();
        List<Map> conf = loginService.queryRoleMenus(id);
        Set<Long> set = conf.stream().map(m -> MapUtils.getLong(m, "key")).collect(Collectors.toSet());
        roleMenu.setConf(set);
        Map<Long, String> level1Map = loginService.queryAllMenuList()
                .stream().collect(Collectors.toMap(m -> m.getId(), m -> m.getName()));
        roleMenu.setAll(loginService.queryMenuList(-1, -1, new MenuEntity()).getList().stream()
                .map(m -> ImmutableMap.of("key", m.getId(), "label", m.getMenuLevel() == 2 ? String.join("/", level1Map.get(m.getParentId()), m.getName()) : m.getName()))
                .collect(Collectors.toList()));
        return ReturnMessage.success(roleMenu);
    }
}
