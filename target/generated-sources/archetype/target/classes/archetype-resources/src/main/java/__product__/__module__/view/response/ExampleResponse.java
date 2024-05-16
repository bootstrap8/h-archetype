#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.view.response;
import com.github.hbq969.code.common.utils.FormatTime;
import lombok.Data;

/**
 * @author : ${author}
 * @description : 示例相应对象
 * @createTime : 2023/8/11 09:52
 */
@Data
public class ExampleResponse {

  private String cacheRefreshTime = FormatTime.YYYYMMDDHHMISS.withMills();
}
