package com.boot.techsupportscheduler.support.interceptor;

import com.boot.techsupportscheduler.support.vo.SessionUser;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Log4j2
public class LoginInterceptor implements HandlerInterceptor {

    private static final AntPathMatcher matcher = new AntPathMatcher();

    // ✅ 비로그인도 허용할 "보기" GET URL들
    private static final List<String> PUBLIC_GET = List.of(
            "/support/home",
            "/support/notice",
            "/support/notice/detail/**",
            "/support/project/list",
            "/support/project/detail/**",
            "/support/support/list",
            "/support/support/detail/**",
            "/support/forbidden"
    );

    private boolean isPublicGet(String uri) {
        return PUBLIC_GET.stream().anyMatch(p -> matcher.match(p, uri));
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        String uri = req.getRequestURI();
        String method = req.getMethod();

        // ✅ GET은 공개 목록이면 통과
        if ("GET".equalsIgnoreCase(method) && isPublicGet(uri)) {
            return true;
        }

        HttpSession session = req.getSession(false);
        SessionUser user = (session == null) ? null : (SessionUser) session.getAttribute("LOGIN_USER");

        if (user == null) {
            log.info("[AUTH] blocked method={} uri={}", method, uri);

            // (옵션) 로그인 후 원래 URL로 돌아가게
            String redirect = URLEncoder.encode(uri, StandardCharsets.UTF_8);
            res.sendRedirect("/login?redirect=" + redirect);

            return false;
        }

        return true;
    }
}
