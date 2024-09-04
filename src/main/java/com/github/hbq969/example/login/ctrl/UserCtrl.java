package com.github.hbq969.example.login.ctrl;

import com.github.hbq969.code.common.restful.ReturnMessage;
import com.github.hbq969.example.login.dao.entity.UserEntity;
import com.github.hbq969.example.login.model.PasswordModify;
import com.github.hbq969.example.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@ConditionalOnProperty(prefix = "login",name = "enabled",havingValue = "true")
@RestController("${artifactId}-web-UserCtrl")
@RequestMapping(path = "/${module}-ui/users")
@Slf4j
@Api(tags = "配置中心-用户管理")
public class UserCtrl {

    @Autowired
    private LoginService loginService;

    @ApiOperation("分页查询用户信息")
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<?> queryUserList(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                          @RequestBody UserEntity q) {
        return ReturnMessage.success(loginService.queryUserList(pageNum, pageSize, q));
    }

    @ApiOperation("新增用户")
    @RequestMapping(path = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<?> saveUser(@RequestBody UserEntity user) {
        loginService.saveUserEntity(user);
        return ReturnMessage.success("保存成功");
    }

    @ApiOperation("修改用户")
    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public ReturnMessage<?> updateUser(@RequestBody UserEntity user) {
        loginService.updateUserEntity(user);
        return ReturnMessage.success("修改成功");
    }

    @ApiOperation("删除用户")
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnMessage<?> deleteUser(@RequestParam(name = "id") Long id) {
        loginService.deleteUserEntity(id);
        return ReturnMessage.success("删除成功");
    }

    @ApiOperation("修改密码")
    @RequestMapping(path = "/user/pass", method = RequestMethod.PUT)
    @ResponseBody
    public ReturnMessage<?> updatePass(@RequestBody PasswordModify modify) {
        loginService.updatePassword(modify);
        return ReturnMessage.success("修改成功");
    }
}
