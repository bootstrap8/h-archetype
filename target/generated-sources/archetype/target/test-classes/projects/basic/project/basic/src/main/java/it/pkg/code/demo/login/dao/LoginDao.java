package it.pkg.code.demo.login.dao;

import it.pkg.code.demo.login.dao.entity.MenuEntity;
import it.pkg.code.demo.login.dao.entity.RoleEntity;
import it.pkg.code.demo.login.dao.entity.UserEntity;
import it.pkg.code.demo.login.model.PasswordModify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginDao {
    void createUsers();

    void createRoles();

    void createMenus();

    void createRoleMenus();

    List<RoleEntity> queryRoleList(RoleEntity q);

    void saveRoleEntity(RoleEntity entity);

    void updateRoleEntity(RoleEntity entity);

    void deleteRoleEntity(@Param("id") Long id);

    List<Map> queryRoleMenus(@Param("id") Long id);

    List<MenuEntity> queryRoleMenus2(@Param("id") Long id);

    List<UserEntity> queryUserList(UserEntity q);

    UserEntity queryUserEntity(@Param("id") Long id);

    void saveUserEntity(UserEntity entity);

    void updateUserEntity(UserEntity entity);

    void deleteUserEntity(@Param("id") Long id);

    void deleteUserEntities(@Param("roleId") Long roleId);

    void updateUserPassword(PasswordModify passwordModify);

    List<MenuEntity> queryMenuList(MenuEntity q);

    void saveMenuEntity(MenuEntity entity);

    void updateMenuEntity(MenuEntity entity);

    void deleteMenuEntity(@Param("id") Long id);

    void deleteMenuEntities(@Param("roleId") Long roleId);

    UserEntity queryUserByName(@Param("name") String name);
}
