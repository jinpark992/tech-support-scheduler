package com.boot.techsupportscheduler.support.interceptor;

import com.boot.techsupportscheduler.support.vo.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

@Log4j2
public class RoleActionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String uri = req.getRequestURI();
        String method = req.getMethod(); // GET, POST

        // ✅ 조회(GET)는 전부 OK (너 요구사항)
        if ("GET".equalsIgnoreCase(method)) {
            return true;
        }

        // ✅ 여기부터는 "변경(POST)"만 권한 체크
        HttpSession session = req.getSession(false);
        SessionUser user = (session == null) ? null : (SessionUser) session.getAttribute("LOGIN_USER");
        if (user == null) {
            res.sendRedirect("/login");
            return false;
        }

        String role = user.getRole();

        // ADMIN은 다 OK
        if ("ROLE_ADMIN".equals(role)) return true;

        // SALES: 프로젝트 관련 POST만 OK
        if ("ROLE_SALES".equals(role)) {
            if (uri.startsWith("/support/project")) return true;
            deny(res, uri);
            return false;
        }

        // SUPPORT: 기술지원 관련 POST만 OK
        if ("ROLE_SUPPORT".equals(role)) {
            if (uri.startsWith("/support/support")) return true;
            deny(res, uri);
            return false;
        }

        // 그 외는 거절
        deny(res, uri);
        return false;
    }

    private void deny(HttpServletResponse res, String uri) throws Exception {
        log.info("[AUTH] forbidden uri={}", uri);
        res.sendRedirect("/support/forbidden");
    }
}