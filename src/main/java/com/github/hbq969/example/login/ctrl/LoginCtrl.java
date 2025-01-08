package com.github.hbq969.example.login.ctrl;

import com.github.hbq969.code.common.encrypt.ext.config.Decrypt;
import com.github.hbq969.code.common.restful.ReturnMessage;
import com.github.hbq969.example.login.model.LoginInfo;
import com.github.hbq969.example.login.model.PermitInfo;
import com.github.hbq969.example.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ConditionalOnProperty(prefix = "login",name = "enabled",havingValue = "true")
@RestController("${artifactId}-web-LoginCtrl")
@RequestMapping(path = "/${module}-ui/system")
@Slf4j
@Api(tags = "配置中心-登录管理")
public class LoginCtrl {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    @Decrypt
    public ReturnMessage<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginInfo info) {
        loginService.login(info, request, response);
        return ReturnMessage.success("登录成功");
    }

    @ApiOperation("注销")
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<?> logout(HttpServletRequest request, HttpServletResponse response) {
        loginService.logout(request, response);
        return ReturnMessage.success("注销成功");
    }

    @ApiOperation("获取账号信息")
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMessage<PermitInfo> getUserInfo(HttpServletRequest request) {
        PermitInfo info = new PermitInfo();
        info.setUser(loginService.getUserInfo(request));
        info.setAllMenus(loginService.queryAllMenuList());
        return ReturnMessage.success(info);
    }
}
