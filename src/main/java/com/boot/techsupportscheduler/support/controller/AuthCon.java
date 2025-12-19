package com.boot.techsupportscheduler.support.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class AuthCon {

    // ✅ 더미 유저(나중에 DB로 교체)
    private final List<Map<String, Object>> users = new ArrayList<>(List.of(
            user("admin", "1234", "관리자", "ADMIN"),
            user("sales", "1234", "영업담당", "SALES"),
            user("support", "1234", "기술담당", "SUPPORT")
    ));

    private Map<String, Object> user(String id, String pw, String name, String role) {
        Map<String, Object> u = new HashMap<>();
        u.put("id", id);
        u.put("pw", pw);
        u.put("name", name);
        u.put("role", role); // ADMIN / SALES / SUPPORT
        return u;
    }

    // 1) 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login"; // templates/auth/login.html
    }

    // 2) 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String id,
                        @RequestParam String pw,
                        HttpSession session,
                        Model model) {

        Map<String, Object> found = users.stream()
                .filter(u -> u.get("id").equals(id) && u.get("pw").equals(pw))
                .findFirst()
                .orElse(null);

        if (found == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀렸어요.");
            return "auth/login";
        }

        // ✅ 세션에 로그인 정보 저장 (이게 “로그인 상태”)
        session.setAttribute("loginUser", found);

        // 일단 메인(대시보드)로 이동
        return "redirect:/support/home";
    }

    // 3) 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // 4) 회원가입 화면
    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("roles", List.of("ADMIN", "SALES", "SUPPORT"));
        return "auth/join"; // templates/auth/join.html
    }

    // 5) 회원가입 처리(더미)
    @PostMapping("/join")
    public String join(@RequestParam String id,
                       @RequestParam String pw,
                       @RequestParam String name,
                       @RequestParam String role) {

        // ✅ 지금은 DB 없으니까 “더미 리스트에 추가”만
        users.add(user(id, pw, name, role));

        // 가입했으면 로그인하러 보내기
        return "redirect:/login";
    }
}
