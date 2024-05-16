#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.dao.entity;

import lombok.Data;

/**
 * @author : ${author}
 * @description : 示例数据库对象实体
 * @createTime : 2023/8/11 09:10
 */
@Data
public class DictEntity {

  private String fieldName;
  private String fieldDesc;
  private String enumType;
}
