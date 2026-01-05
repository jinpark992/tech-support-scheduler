package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.service.JoinSvc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class JoinCon {

    private final JoinSvc joinSvc;

    // ✅ 역할 목록은 한 곳에서만 관리 (중복 제거)
    private static final List<String> ROLES =
            List.of("ROLE_ADMIN", "ROLE_SALES", "ROLE_SUPPORT");

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("roles", ROLES);
        model.addAttribute("selectedRole", ""); // 초기값(선택 없음)
        return "auth/join";
    }

    @PostMapping("/join")
    public String joinExe(@RequestParam String loginId,
                          @RequestParam String password,
                          @RequestParam String passwordConfirm,
                          @RequestParam String name,
                          @RequestParam String role,
                          Model model) {

        try {
            joinSvc.join(loginId, password, passwordConfirm, name, role);
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());

            // 입력값 복원(비번 제외)
            model.addAttribute("loginId", loginId);
            model.addAttribute("name", name);

            // ✅ 여기 버그였음: roles로 넣어야 함 (role 아님)
            model.addAttribute("roles", ROLES);

            // ✅ 사용자가 선택했던 role 복원
            model.addAttribute("selectedRole", role);

            return "auth/join";
        }
    }
}
