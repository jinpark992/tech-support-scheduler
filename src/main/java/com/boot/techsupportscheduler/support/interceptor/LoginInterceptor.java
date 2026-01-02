package com.boot.techsupportscheduler.support.interceptor;

import com.boot.techsupportscheduler.support.vo.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

@Log4j2
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        String uri = req.getRequestURI();

        // (참고) /support/**만 막을 거라 아래 예외는 사실 없어도 됨.
        // 그래도 안전하게 정적/인증 URL은 통과시켜둠.
        if (uri.startsWith("/login") || uri.startsWith("/join")
                || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/images")) {
            return true;
        }

        HttpSession session = req.getSession(false);
        SessionUser user = (session == null) ? null : (SessionUser) session.getAttribute("LOGIN_USER");

        if (user == null) {
            log.info("[AUTH] blocked uri={}", uri);
            res.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
