package it.pkg.code.demo.login.ctrl;

import com.github.hbq969.code.common.restful.ReturnMessage;
import it.pkg.code.demo.login.dao.entity.MenuEntity;
import it.pkg.code.demo.login.service.LoginService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@ConditionalOnProperty(prefix = "login",name = "enabled",havingValue = "true")
@RestController("basic-web-MenuCtrl")
@RequestMapping(path = "/demo-ui/menus")
@Slf4j
@Api(tags = "配置中心-菜单管理")
public class MenuCtrl {

    @Autowired
    private LoginService loginService;

    @ApiOperation("查询菜单信息")
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<PageInfo<MenuEntity>> queryMenuList(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                             @RequestBody MenuEntity q) {

        return ReturnMessage.success(loginService.queryMenuList(pageNum, pageSize, q));
    }

    @ApiOperation("查询菜单信息")
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMessage<PageInfo<MenuEntity>> queryMenuList() {

        return ReturnMessage.success(loginService.queryMenuList(-1, -1, new MenuEntity()));
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
