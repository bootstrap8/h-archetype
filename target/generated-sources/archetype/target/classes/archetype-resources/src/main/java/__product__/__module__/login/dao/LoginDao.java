#set($symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.login.dao;

import ${package}.${product}.${module}.login.dao.entity.MenuEntity;
import ${package}.${product}.${module}.login.dao.entity.RoleEntity;
import ${package}.${product}.${module}.login.dao.entity.UserEntity;
import ${package}.${product}.${module}.login.model.PasswordModify;
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

    void deleteRoleEntity(@Param("app") String app, @Param("id") Long id);

    List<Map> queryRoleMenus(@Param("app") String app, @Param("id") Long id);

    List<MenuEntity> queryRoleMenus2(@Param("app") String app, @Param("id") Long id);

    List<UserEntity> queryUserList(UserEntity q);

    UserEntity queryUserEntity(@Param("app") String app, @Param("id") Long id);

    void saveUserEntity(UserEntity entity);

    void updateUserEntity(UserEntity entity);

    void deleteUserEntity(@Param("app") String app, @Param("id") Long id);

    void deleteUserEntities(@Param("app") String app, @Param("roleId") Long roleId);

    void updateUserPassword(@Param("app") String app, @Param("modify") PasswordModify passwordModify);

    List<MenuEntity> queryMenuList(MenuEntity q);

    void saveMenuEntity(MenuEntity entity);

    void updateMenuEntity(MenuEntity entity);

    void deleteMenuEntity(@Param("app") String app, @Param("id") Long id);

    void deleteMenuEntities(@Param("app") String app, @Param("roleId") Long roleId);

    void deleteMenuForRole(@Param("app") String app, @Param("menuId") Long menuId);
    UserEntity queryUserByName(@Param("app") String app, @Param("name") String name);
}
