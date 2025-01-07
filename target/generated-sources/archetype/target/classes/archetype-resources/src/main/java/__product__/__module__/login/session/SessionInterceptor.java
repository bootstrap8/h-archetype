#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.login.session;

import com.github.hbq969.code.common.restful.Result;
import com.github.hbq969.code.common.spring.interceptor.AbstractHandlerInterceptor;
import com.github.hbq969.code.common.utils.GsonUtils;
import ${package}.${product}.${module}.config.Config;
import ${package}.${product}.${module}.login.service.LoginService;
import ${package}.${product}.${module}.login.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@ConditionalOnProperty(prefix = "login",name = "enabled",havingValue = "true")
@Component("${artifactId}-SessionInterceptor")
@Slf4j
public class SessionInterceptor extends AbstractHandlerInterceptor {

    @Autowired
    private LoginService loginService;
    private long currentTimeMillis = 0;

    @Autowired
    private Config conf;

    @Override
    public List<String> getExcludedPathPatterns() {
        return conf.getExcludeUrls();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = false;
        String jsid = CookieUtils.getCookieValue(request, "JSESSIONID");
        String sid = null;
        if (StringUtils.contains(jsid, ".")) {
            sid = jsid.substring(0, jsid.indexOf("."));
        }
        if (StringUtils.isEmpty(sid)) {
        } else {
            HttpSession hs = loginService.getSession(sid);
            if (hs == null) {
            } else if (null != hs.getAttribute("user")) {
                result = true;
                if (currentTimeMillis == 0 || System.currentTimeMillis() - currentTimeMillis > 5000) {
                    Cookie jsessionCookie = new Cookie("JSESSIONID", jsid);
                    jsessionCookie.setMaxAge((int) conf.getCookieMaxAgeSec());
                    jsessionCookie.setPath("/");
                    jsessionCookie.setHttpOnly(true);
                    response.addCookie(jsessionCookie);
                    currentTimeMillis = System.currentTimeMillis();
                }
            }
        }
        if (!result) {
            log.warn("会话失效，或已注销");
            invalidateSession(response);
        }
        return result;
    }

    private static void invalidateSession(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(GsonUtils.toJson(Result.fail("会话失效")));
    }

    @Override
    public int order() {
        return Integer.MIN_VALUE + 1;
    }

}
