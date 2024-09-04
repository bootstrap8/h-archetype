package it.pkg.code.demo.login.service;

import it.pkg.code.demo.login.dao.entity.*;
import it.pkg.code.demo.login.model.UserInfo;
import it.pkg.code.demo.login.model.LoginInfo;
import it.pkg.code.demo.login.model.PasswordModify;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface LoginService {
    PageInfo<RoleEntity> queryRoleList(int pageNum, int pageSize, RoleEntity q);

    void saveRoleEntity(RoleEntity entity);

    void updateRoleEntity(RoleEntity entity);

    @Transactional(rollbackFor = Exception.class)
    void deleteRoleEntity(Long id);

    List<Map> queryRoleMenus(Long id);

    PageInfo<UserEntity> queryUserList(int pageNum, int pageSize, UserEntity q);

    void saveUserEntity(UserEntity entity);

    void updateUserEntity(UserEntity entity);

    void deleteUserEntity(Long id);

    void updatePassword(PasswordModify passwordModify);

    PageInfo<MenuEntity> queryMenuList(int pageNum, int pageSize, MenuEntity q);

    void saveMenuEntity(MenuEntity entity);

    void updateMenuEntity(MenuEntity entity);

    void deleteMenuEntity(Long id);

    @Transactional(rollbackFor = Exception.class)
    void updateRoleMenus(RoleMenuEntity roleMenuEntity);

    void login(LoginInfo login, HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);

    UserInfo getUserInfo(HttpServletRequest request);

    HttpSession getSession(String sid);
}
