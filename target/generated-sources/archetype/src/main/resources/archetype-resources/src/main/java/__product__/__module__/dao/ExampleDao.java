#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.dao;

import ${package}.${product}.${module}.dao.entity.DictEntity;
import com.github.hbq969.code.common.datasource.DS;
import com.github.hbq969.code.common.datasource.context.DefaultPolicy;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : ${author}
 * @description : 示例数据访问层接口
 * @createTime : 2023/8/11 09:05
 */
@Mapper
@DS(DefaultPolicy.class)
public interface ExampleDao {

  List<DictEntity> queryList();
}
