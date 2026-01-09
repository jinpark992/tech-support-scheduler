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

        // ✅ 조회(GET)는 전부 OK
        if ("GET".equalsIgnoreCase(method)) return true;

        // ✅ GET 외(POST/PUT/DELETE 등) 권한 체크
        HttpSession session = req.getSession(false);
        SessionUser user = (session == null) ? null : (SessionUser) session.getAttribute("LOGIN_USER");
        if (user == null) {
            res.sendRedirect("/login?redirect=" + uri);
            return false;
        }

// ✅ 내정보/비번변경은 전 역할 허용
        if (uri.startsWith("/support/user/")) return true;

        // ✅ [수정] 좋아요(/like) 뿐만 아니라 댓글(/comment) 요청도 모두 통과!
        // 이렇게 하면 '/support/notice/comment/add' 요청도 들어갈 수 있어.
        if (uri.contains("/like") || uri.contains("/comment")) return true;

        String role = user.getRole();


        // ADMIN은 다 OK
        if ("ROLE_ADMIN".equals(role)) return true;

        // SALES: 프로젝트 관련 POST만 OK
        if ("ROLE_SALES".equals(role)) {
            if (uri.startsWith("/support/project")) return true;
            deny(req, res);
            return false;
        }

        // SUPPORT: 기술지원 관련 POST만 OK
        if ("ROLE_SUPPORT".equals(role)) {
            if (uri.startsWith("/support/support")) return true;
            deny(req, res);
            return false;
        }

        // 그 외는 거절
        deny(req, res);
        return false;
    }

    private void deny(HttpServletRequest req, HttpServletResponse res) throws Exception {
        res.setStatus(403);
        res.setContentType("text/html; charset=UTF-8");

        // referer는 "경로"만 쓰자 (깨짐/보안 이슈 방지)
        String referer = req.getHeader("Referer");
        String fallback = "/support/home";

        String backUrl = fallback;
        if (referer != null && !referer.isBlank()) {
            // localhost:8088 같은 풀 URL이 와도 /support/... 경로만 잘라서 사용
            int idx = referer.indexOf("/support/");
            if (idx != -1) backUrl = referer.substring(idx);
        }

        // JS 문자열 깨짐 방지
        backUrl = backUrl.replace("'", "\\'");

        res.getWriter().write("""
            <script>
              alert('권한이 없습니다.');
              location.replace('%s');
            </script>
        """.formatted(backUrl));
        res.getWriter().flush();
    }
}
