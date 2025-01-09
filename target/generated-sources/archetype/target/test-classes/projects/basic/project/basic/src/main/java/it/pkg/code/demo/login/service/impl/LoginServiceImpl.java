package it.pkg.code.demo.login.service.impl;

import com.github.hbq969.code.common.spring.context.SpringContext;
import com.github.hbq969.code.common.utils.GsonUtils;
import com.github.hbq969.code.common.utils.InitScriptUtils;
import com.github.hbq969.code.dict.service.api.impl.MapDictHelperImpl;
import it.pkg.code.demo.config.Config;
import it.pkg.code.demo.login.dao.LoginDao;
import it.pkg.code.demo.login.dao.entity.MenuEntity;
import it.pkg.code.demo.login.dao.entity.RoleEntity;
import it.pkg.code.demo.login.dao.entity.RoleMenuEntity;
import it.pkg.code.demo.login.dao.entity.UserEntity;
import it.pkg.code.demo.login.model.LoginInfo;
import it.pkg.code.demo.login.model.PasswordModify;
import it.pkg.code.demo.login.model.UserInfo;
import it.pkg.code.demo.login.service.LoginService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ConditionalOnProperty(prefix = "login", name = "enabled", havingValue = "true")
@Service("basic-LoginServiceImpl")
@Slf4j
public class LoginServiceImpl implements LoginService, InitializingBean {

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private SpringContext context;

    @Autowired
    private Config conf;

    @Autowired
    private MapDictHelperImpl dict;

    @Value("${spring.application.name}")
    private String app;

    private Cache<String, HttpSession> sessions;

    @Override
    public void afterPropertiesSet() throws Exception {
        initialData();
        log.info("配置的cookie、会话超时时间: {} 秒。", conf.getCookieMaxAgeSec());
        this.sessions = CacheBuilder.newBuilder().maximumSize(500).initialCapacity(100).concurrencyLevel(10).expireAfterAccess(conf.getCookieMaxAgeSec(), TimeUnit.SECONDS).removalListener((RemovalListener<String, HttpSession>) notif -> {
            log.info("session自动过期，sid: {}", notif.getKey());
            notif.getValue().invalidate();
        }).build();
    }

    private void initialData() {
        try {
            loginDao.createRoles();
        } catch (Exception e) {
        }
        try {
            loginDao.createMenus();
        } catch (Exception e) {
        }
        try {
            loginDao.createUsers();
        } catch (Exception e) {
        }
        try {
            loginDao.createRoleMenus();
        } catch (Exception e) {
        }
        InitScriptUtils.initial(context, "/", "login-data.sql", null, StandardCharsets.UTF_8, () -> {
            dict.reloadImmediately();
        });
    }

    @Override
    public PageInfo<RoleEntity> queryRoleList(int pageNum, int pageSize, RoleEntity q) {
        q.withApp(context);
        if (pageNum < 0) {
            PageInfo<RoleEntity> pg = new PageInfo<>(loginDao.queryRoleList(q));
            return pg;
        } else {
            PageInfo<RoleEntity> pg = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> loginDao.queryRoleList(q));
            pg.getList().forEach(e -> e.convertDict(context));
            return pg;
        }
    }

    @Override
    public void saveRoleEntity(RoleEntity entity) {
        entity.initial();
        entity.withApp(context);
        loginDao.saveRoleEntity(entity);
    }

    @Override
    public void updateRoleEntity(RoleEntity entity) {
        entity.update();
        entity.withApp(context);
        loginDao.updateRoleEntity(entity);
    }

    @Override
    public void deleteRoleEntity(Long id) {
        loginDao.deleteMenuEntities(app, id);
        loginDao.deleteUserEntities(app, id);
        loginDao.deleteRoleEntity(app, id);
    }

    @Override
    public List<Map> queryRoleMenus(Long id) {
        return loginDao.queryRoleMenus(app, id);
    }

    @Override
    public PageInfo<UserEntity> queryUserList(int pageNum, int pageSize, UserEntity q) {
        q.setApp(app);
        PageInfo<UserEntity> pg = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> loginDao.queryUserList(q));
        pg.getList().forEach(e -> e.convertDict(context));
        return pg;
    }

    @Override
    public void saveUserEntity(UserEntity entity) {
        entity.initial();
        entity.setApp(app);
        loginDao.saveUserEntity(entity);
    }

    @Override
    public void updateUserEntity(UserEntity entity) {
        entity.update();
        entity.setApp(app);
        loginDao.updateUserEntity(entity);
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        resetSessionUserInfo(sra.getRequest(), null);
    }

    @Override
    public void deleteUserEntity(Long id) {
        loginDao.deleteUserEntity(app, id);
    }

    @Override
    public void updatePassword(PasswordModify passwordModify) {
        UserEntity ue = loginDao.queryUserEntity(app, passwordModify.getId());
        if (ue == null) {
            throw new UnsupportedOperationException("用户不存在");
        }
        if (!StringUtils.equals(passwordModify.getOldPassword(), ue.getPassword())) {
            throw new IllegalArgumentException("老密码不对");
        }
        loginDao.updateUserPassword(app, passwordModify);
    }

    @Override
    public PageInfo<MenuEntity> queryMenuList(int pageNum, int pageSize, MenuEntity q) {
        q.setApp(app);
        if (pageNum < 0) {
            List<MenuEntity> menus = loginDao.queryMenuList(q);
            PageInfo<MenuEntity> pg = new PageInfo<>(menus);
            pg.setTotal(menus.size());
            return pg;
        } else {
            PageInfo<MenuEntity> pg = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> loginDao.queryMenuList(q));
            pg.getList().forEach(e -> e.convertDict(context));
            return pg;
        }
    }

    @Override
    public List<MenuEntity> queryAllMenuList() {
        MenuEntity me = new MenuEntity();
        me.setApp(app);
        List<MenuEntity> list = loginDao.queryMenuList(me);
        return groupSortMenu(list);
    }

    @Override
    public void saveMenuEntity(MenuEntity entity) {
        entity.initial();
        entity.setApp(app);
        loginDao.saveMenuEntity(entity);
    }

    @Override
    public void updateMenuEntity(MenuEntity entity) {
        entity.update();
        entity.setApp(app);
        loginDao.updateMenuEntity(entity);
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        resetSessionUserInfo(sra.getRequest(), null);
    }

    @Override
    public void deleteMenuEntity(Long id) {
        loginDao.deleteMenuEntity(app, id);
        loginDao.deleteMenuForRole(app, id);
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        resetSessionUserInfo(sra.getRequest(), null);
    }

    @Override
    public void updateRoleMenus(RoleMenuEntity roleMenuEntity) {
        loginDao.deleteMenuEntities(app, roleMenuEntity.getRole().getId());
        context.getBean(JdbcTemplate.class).batchUpdate("insert into h_role_menus(app,role_id,menu_id) values(?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                MenuEntity me = roleMenuEntity.getMenus().get(i);
                log.info("保存菜单权限, role_id: {}, menu_id: {}", roleMenuEntity.getRole().getId(), me.getId());
                ps.setString(1, app);
                ps.setLong(2, roleMenuEntity.getRole().getId());
                ps.setLong(3, me.getId());
            }

            @Override
            public int getBatchSize() {
                return roleMenuEntity.getMenus().size();
            }
        });
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        resetSessionUserInfo(sra.getRequest(), null);
    }

    @Override
    public void login(LoginInfo login, HttpServletRequest request, HttpServletResponse response) {
        UserEntity user = loginDao.queryUserByName(app, login.getUsername());
        log.info("查询到用户信息: {}", user);
        if (StringUtils.equals(user.getPassword(), login.getPassword())) {
            log.info("密码验证一致");
            resetSessionUserInfo(request, user);
        } else {
            throw new IllegalArgumentException("密码错误，请重试");
        }
    }

    private void resetSessionUserInfo(HttpServletRequest request, UserEntity user) {
        HttpSession session;
        String logKey = "创建";
        if (user == null) {
            session = request.getSession();
            UserInfo oldUser = (UserInfo) session.getAttribute("user");
            user = loginDao.queryUserByName(app, oldUser.getUserName());
            logKey = "更新";
        } else {
            session = request.getSession(true);
        }
        // 创建会话对象
        UserInfo ui = new UserInfo();
        ui.setUserId(user.getId());
        ui.setUserName(user.getUsername());
        ui.setRoleId(user.getRoleId());
        ui.setRoleName(user.getRoleName());
        List<MenuEntity> list = loginDao.queryRoleMenus2(app, user.getRoleId());
        List<MenuEntity> confMenus = groupSortMenu(list);
        ui.setMenus(confMenus);
        session.setAttribute("user", ui);
        log.info("{}会话, id: {}, user: {}", logKey, session.getId(), GsonUtils.toJson(ui));
        if (StringUtils.equals("创建", logKey)) {
            sessions.put(session.getId(), session);
        }
    }

    // 对菜单进行分组排序
    private List<MenuEntity> groupSortMenu(List<MenuEntity> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        List<MenuEntity> level1List = menus.stream().filter(m -> m.getMenuLevel() == 1).sorted(Comparator.comparing(m -> m.getOrderIndex())).collect(Collectors.toList());
        Map<Long, List<MenuEntity>> level2Group = menus.stream().filter(m -> m.getMenuLevel() == 2).collect(Collectors.groupingBy(m -> m.getParentId()));
        for (Map.Entry<Long, List<MenuEntity>> e : level2Group.entrySet()) {
            e.getValue().stream().sorted(Comparator.comparing(m -> m.getOrderIndex())).collect(Collectors.toList());
        }
        for (MenuEntity m : level1List) {
            List<MenuEntity> sub = level2Group.get(m.getId());
            if (CollectionUtils.isNotEmpty(sub)) {
                m.setMenus(sub);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("权限内的菜单数据: {}", GsonUtils.toJson(level1List));
        }
        return level1List;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            log.info("注销账号: {}, 会话: {}", session.getAttribute("user"), session.getId());
            sessions.invalidate(session.getId());
            Cookie jsessionCookie = new Cookie("JSESSIONID", null);
            jsessionCookie.setPath("/");
            jsessionCookie.setHttpOnly(true);
            response.addCookie(jsessionCookie);
        }
    }

    @Override
    public HttpSession getSession(String sid) {
        return sessions.getIfPresent(sid);
    }

    @Scheduled(fixedRate = 5000)
    void cleanExpiredSessions() {
        sessions.cleanUp();
    }

    @Override
    public UserInfo getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            return (UserInfo) session.getAttribute("user");
        } else {
            throw new RuntimeException("会话失效，请重新登录");
        }
    }
}

