#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.login.model;

import ${package}.${product}.${module}.login.dao.entity.MenuEntity;
import lombok.Data;

import java.util.List;

@Data
public class PermitInfo {
    private UserInfo user;
    private List<MenuEntity> allMenus;

    public boolean isAdmin() {
        return user != null && user.isAdmin();
    }
}
