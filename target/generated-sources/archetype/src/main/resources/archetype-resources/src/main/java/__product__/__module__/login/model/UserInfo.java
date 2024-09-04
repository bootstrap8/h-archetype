#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.login.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserInfo extends com.github.hbq969.code.common.spring.context.UserInfo {
    private Long userId;
    private Long roleId;
    private String roleName;
    private List<Map> menus;
}
