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

    // ✅ 서비스 계층(비즈니스 로직) 주입
    private final JoinSvc joinSvc;

    /**
     * ✅ 회원가입 화면 요청 (GET /join)
     *
     * - 브라우저에서 /join 으로 들어오면 여기로 옴
     * - join.html에서 roles 리스트를 렌더링하려면 model에 roles를 담아줘야 함
     * - return "auth/join" => templates/auth/join.html 렌더링
     */
    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("roles", List.of("ROLE_ADMIN", "ROLE_SALES", "ROLE_SUPPORT"));
        return "auth/join";
    }

    /**
     * ✅ 회원가입 처리 (POST /join)
     *
     * - join.html의 form submit이 여기로 들어옴
     * - RequestParam 이름은 join.html의 input/select name과 반드시 일치해야 함
     *   (loginId/password/passwordConfirm/name/role)
     * - 성공하면 로그인 페이지로 redirect
     * - 실패하면 다시 join 화면으로 돌아가면서 에러 메시지를 뿌림
     */
    @PostMapping("/join")
    public String joinExe(@RequestParam String loginId,
                          @RequestParam String password,
                          @RequestParam String passwordConfirm,
                          @RequestParam String name,
                          @RequestParam String role,
                          Model model) {

        try {
            // ✅ 핵심: 실제 회원 생성은 서비스에서 수행
            joinSvc.join(loginId, password, passwordConfirm, name, role);

            // ✅ 가입 성공 → 로그인 페이지로 이동
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            // ✅ 가입 실패 → join 화면 재렌더링하면서 메시지 표시

            model.addAttribute("error", e.getMessage());

            // 사용자가 입력했던 값은 다시 채워주면 UX 좋아짐 (비번은 보안상 재입력)
            model.addAttribute("loginId", loginId);
            model.addAttribute("name", name);

            // roles도 다시 넣어줘야 화면에서 role 드롭다운이 깨지지 않음
            model.addAttribute("role", List.of("ROLE_ADMIN", "ROLE_SALES", "ROLE_SUPPORT"));

            return "auth/join";
        }
    }
}
