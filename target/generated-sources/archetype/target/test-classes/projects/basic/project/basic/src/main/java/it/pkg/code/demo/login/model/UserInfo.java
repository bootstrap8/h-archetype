package it.pkg.code.demo.login.model;

import it.pkg.code.demo.login.dao.entity.MenuEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class UserInfo extends com.github.hbq969.code.common.spring.context.UserInfo {
    private Long userId;
    private Long roleId;
    private String roleName;
    private List<MenuEntity> menus;

    public boolean isAdmin() {
        return StringUtils.equals("ADMIN", roleName);
    }
}

