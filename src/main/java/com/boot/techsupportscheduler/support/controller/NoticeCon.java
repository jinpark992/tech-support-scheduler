package com.boot.techsupportscheduler.support.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/support")
public class NoticeCon {

    // DB 아직 안 붙였으니까 더미 목록
    private final List<Notice> dummy = List.of(
            new Notice(1, true,  "기술지원 사이트 오픈 안내", "2024-05-02", 5,  "오픈 공지 내용입니다."),
            new Notice(2, false, "공지사항 테스트2",         "2024-04-30", 9,  "테스트 공지 내용입니다."),
            new Notice(3, false, "공지사항 테스트입니다",     "2024-04-28", 12, "테스트 공지 내용입니다.")
    );

    // 1) 목록 화면 (대시보드/공지사항 목록)
    // URL: http://localhost:8086/support/notice
    @GetMapping("/notice")
    public String list(Model model) {
        model.addAttribute("activeMenu", "home"); // 헤더 메뉴 활성화용
        model.addAttribute("notices", dummy);     // home.html에서 th:each로 쓰는 데이터
        return "home/home";                       // templates/home/home.html
    }

    // 2) 등록 폼 화면
    // URL: http://localhost:8086/support/notice/new
    @GetMapping("/notice/new")
    public String form(Model model) {
        model.addAttribute("activeMenu", "home");
        return "notice/form"; // templates/notice/form.html
    }

    // 3) 상세 화면 (RequestParam 방식)
    // URL: http://localhost:8086/support/notice/detail?id=1
    @GetMapping("/notice/detail")
    public String detail(@RequestParam int id, Model model) {
        model.addAttribute("activeMenu", "home");

        Notice notice = dummy.stream()
                .filter(n -> n.id() == id)
                .findFirst()
                .orElse(null);

        model.addAttribute("notice", notice); // detail.html에서 사용
        return "notice/detail"; // templates/notice/detail.html
    }

    // DTO (임시)
    public record Notice(int id, boolean top, String title, String writeDate, int views, String content) {}
}
