package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.service.UserSvc;
import com.boot.techsupportscheduler.support.vo.SessionUser;
import com.boot.techsupportscheduler.support.vo.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * ✅ 내 정보 수정 + 비밀번호 변경 컨트롤러
 *
 * - /support/user 하위 URL을 담당
 * - 로그인된 사용자(세션 LOGIN_USER)를 기준으로 동작 (userId로 DB 조회/수정)
 * - 화면 렌더링: GET
 * - 처리(저장/변경): POST
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/support/user") // ✅ 이 컨트롤러의 기본 경로(prefix)
public class UserCon {

    // ✅ 비즈니스 로직은 Service에게 위임
    private final UserSvc userSvc;

    /**
     * ✅ 내 정보 수정 화면
     *
     * GET /support/user/edit
     *
     * 흐름:
     * 1) 세션에서 로그인 유저(SessionUser) 꺼냄
     * 2) userId로 DB에서 내 정보를 조회(User me)
     * 3) model에 "user"로 담아서 edit.html에 뿌림
     */
    @GetMapping("/edit")
    public String editForm(HttpSession session, Model model) {

        // ✅ 로그인 여부 확인: 로그인 성공 시 세션에 저장해 둔 "LOGIN_USER"를 꺼냄
        SessionUser su = (SessionUser) session.getAttribute("LOGIN_USER");

        // ✅ 세션에 없으면 로그인 안 했거나 만료 → 로그인 페이지로 보냄
        if (su == null) return "redirect:/login";

        // ✅ DB에서 내 정보 조회 (표시용 데이터)
        User me = userSvc.getMyInfo(su.getUserId());

        // ✅ 화면에서 ${user.xxx}로 꺼낼 수 있도록 model에 넣음
        model.addAttribute("user", me);

        // ✅ templates/user/edit.html 렌더링
        return "user/edit";
    }

    /**
     * ✅ 내 정보 수정 처리(이름 변경)
     *
     * POST /support/user/edit
     *
     * - edit.html의 form submit이 여기로 들어옴
     * - name 파라미터를 받아서 DB UPDATE 수행
     * - 성공하면 redirect로 다시 edit 화면 (새로고침 중복 제출 방지)
     * - 실패하면 같은 화면으로 돌아가면서 error 메시지 표시
     */
    @PostMapping("/edit")
    public String editExe(@RequestParam String name,
                          HttpSession session,
                          Model model) {

        // ✅ 로그인 유저 확인
        SessionUser su = (SessionUser) session.getAttribute("LOGIN_USER");
        if (su == null) return "redirect:/login";

        try {
            // ✅ 핵심: 이름 수정 로직은 서비스가 담당(검증 + DAO 호출)
            userSvc.updateMyName(su.getUserId(), name);

            // ✅ 성공 → 다시 edit 화면으로
            // (PRG 패턴: Post-Redirect-Get)
            return "redirect:/support/user/edit";

        } catch (IllegalArgumentException e) {
            // ✅ 실패 → error 메시지 출력 + 화면 재렌더링

            // 화면에 다시 뿌릴 사용자 정보 재조회
            User me = userSvc.getMyInfo(su.getUserId());

            model.addAttribute("user", me);
            model.addAttribute("error", e.getMessage());

            // ✅ templates/user/edit.html 재렌더링
            return "user/edit";
        }
    }

    /**
     * ✅ 비밀번호 변경 화면
     *
     * GET /support/user/password
     *
     * - 비밀번호 변경 폼만 보여줌
     * - (GET 매핑이 없으면 링크 클릭 시 405 에러 남)
     */
    @GetMapping("/password")
    public String passwordForm(HttpSession session) {

        // ✅ 로그인 체크
        SessionUser su = (SessionUser) session.getAttribute("LOGIN_USER");
        if (su == null) return "redirect:/login";

        // ✅ templates/user/password.html 렌더링
        return "user/password";
    }

    /**
     * ✅ 비밀번호 변경 처리
     *
     * POST /support/user/password
     *
     * - password.html의 form submit이 여기로 들어옴
     * - 현재 비밀번호 검증(BCrypt.checkpw) + 새 비밀번호 해시 생성 후 업데이트
     * - 성공하면 edit 화면으로 이동
     * - 실패하면 password 화면에 error 메시지 표시
     */
    @PostMapping("/password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String newPasswordConfirm,
                                 HttpSession session,
                                 Model model) {

        // ✅ 로그인 체크
        SessionUser su = (SessionUser) session.getAttribute("LOGIN_USER");
        if (su == null) return "redirect:/login";

        try {
            // ✅ 핵심: 서비스에서
            // 1) 현재 비번 맞는지 확인
            // 2) 새 비번/확인 일치 검증
            // 3) BCrypt로 해시 생성
            // 4) DB의 password_hash 업데이트
            userSvc.changeMyPassword(su.getUserId(), currentPassword, newPassword, newPasswordConfirm);

            // ✅ 성공 → 내 정보 수정 화면으로
            return "redirect:/support/user/edit";

        } catch (IllegalArgumentException e) {
            // ✅ 실패 → 비번 변경 화면 재렌더링 + 에러 메시지 표시
            model.addAttribute("error", e.getMessage());
            return "user/password";
        }
    }
}
