#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.service;

import ${package}.${product}.${module}.dao.entity.DictEntity;
import java.util.List;

/**
 * @author : ${author}
 * @description : 示例业务接口
 * @createTime : 2023/8/11 09:44
 */
public interface ExampleService {

  List<DictEntity> queryList(String key);

}
