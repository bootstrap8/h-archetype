package it.pkg.code.demo.login.model;

import it.pkg.code.demo.login.dao.entity.MenuEntity;
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
