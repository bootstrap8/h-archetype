#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.feign;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author : ${author}
 * @description : Feign客户端桩代码接口
 * @createTime : 2023/8/26 20:17
 */
public interface DemoService {

  @RequestMapping(path = "/s", method = RequestMethod.GET)
  String query();
}
