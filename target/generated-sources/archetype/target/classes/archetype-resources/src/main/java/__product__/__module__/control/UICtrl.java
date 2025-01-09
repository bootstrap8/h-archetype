#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.${product}.${module}.control;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  hbq969@gmail.com
 * @description :
 * @createTime : 2024/11/29 12:39
 */
@RestController
@Api(tags = "示例接口")
@Slf4j
@RequestMapping(path = "/${module}-ui")
public class UICtrl {
    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello World!";
    }
}
