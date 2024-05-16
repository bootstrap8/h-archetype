#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.view.request;

import lombok.Data;

/**
 * @author : ${author}
 * @description : 示例请求对象
 * @createTime : 2023/8/11 09:52
 */
@Data
public class ExampleRequest {

  private String foo;
  private String bar;
}
