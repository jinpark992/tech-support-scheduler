package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.service.LoginSvc;
import com.boot.techsupportscheduler.support.vo.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequiredArgsConstructor
public class LoginCon {

    private final LoginSvc loginSvc;

    // ✅ 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    // ✅ 로그인 처리
    @PostMapping("/login")
    public String loginExe(@RequestParam("id") String id,
                           @RequestParam("pw") String pw,
                           HttpSession session,
                           Model model) {



        log.info("LOGIN TRY id={}", id);

        SessionUser su = loginSvc.login(id, pw);

        log.info("LOGIN RESULT success={}", (su != null));

        if (su == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
            return "auth/login";
        }



        session.setAttribute("LOGIN_USER", su);

        return "redirect:/support/project/list";
    }

    // ✅ 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/support/forbidden")
    public String forbidden() {
        return "comm/forbidden"; // 너 템플릿 위치에 맞게
    }

}
