package com.github.hbq969.example.login.model;

import com.github.hbq969.example.login.dao.entity.MenuEntity;
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
