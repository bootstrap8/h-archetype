#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.login.ctrl;

import com.github.hbq969.code.common.restful.ReturnMessage;
import ${package}.${product}.${module}.login.model.LoginInfo;
import ${package}.${product}.${module}.login.model.UserInfo;
import ${package}.${product}.${module}.login.service.LoginService;
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
    public ReturnMessage<UserInfo> getUserInfo(HttpServletRequest request) {
        return ReturnMessage.success(loginService.getUserInfo(request));
    }
}
