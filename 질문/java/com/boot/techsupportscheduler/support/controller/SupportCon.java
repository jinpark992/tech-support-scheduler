package com.boot.techsupportscheduler.support.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/support/support")
public class SupportCon {

    // ✅ 더미 프로젝트 목록 (나중에 ProjectCon과 Service로 합치면 됨)
    private List<Map<String, Object>> dummyProjects() {
        List<Map<String, Object>> projects = new ArrayList<>();

        Map<String, Object> p1 = new HashMap<>();
        p1.put("id", 1);
        p1.put("name", "OO사 유지보수");
        projects.add(p1);

        Map<String, Object> p2 = new HashMap<>();
        p2.put("id", 2);
        p2.put("name", "XX사 설치지원");
        projects.add(p2);

        return projects;
    }

    // ✅ 더미 기술지원 일정 목록 (캘린더에 뿌릴 데이터)
    private List<Map<String, Object>> dummySupports() {
        List<Map<String, Object>> supports = new ArrayList<>();

        Map<String, Object> s1 = new HashMap<>();
        s1.put("id", 1);
        s1.put("date", "2025-12-19");
        s1.put("projectId", 1);
        s1.put("projectName", "OO사 유지보수");
        s1.put("type", "장애지원"); // 유지보수/설치지원/장애지원 중 하나
        s1.put("title", "서버 접속 불가");
        s1.put("content", "오전 10시부터 접속이 안된다고 연락옴");
        supports.add(s1);

        Map<String, Object> s2 = new HashMap<>();
        s2.put("id", 2);
        s2.put("date", "2025-12-20");
        s2.put("projectId", 2);
        s2.put("projectName", "XX사 설치지원");
        s2.put("type", "설치지원");
        s2.put("title", "초기 설치 진행");
        s2.put("content", "현장 방문 설치 예정");
        supports.add(s2);

        return supports;
    }

    // 1) ✅ 캘린더 화면
    // URL: /support/support
    @GetMapping("/list")
    public String calendar(Model model) {
        model.addAttribute("activeMenu", "support");
        model.addAttribute("supports", dummySupports()); // 캘린더에 찍을 일정들
        return "support/calendar"; // templates/support/calendar.html
    }

    // 2) ✅ 기술지원 등록 폼
    // URL: /support/support/new?date=2025-12-19
    @GetMapping("/new")
    public String form(@RequestParam(required = false) String date, Model model) {
        model.addAttribute("activeMenu", "support");

        // 폼 상단에서 프로젝트 선택할 수 있게 프로젝트 목록 같이 던짐
        model.addAttribute("projects", dummyProjects());

        // 체크박스(유지보수/설치지원/장애지원) 옵션
        model.addAttribute("supportTypes", List.of("유지보수", "설치지원", "장애지원"));

        // 캘린더에서 날짜 눌러서 들어오면 date가 넘어오는 구조
        model.addAttribute("selectedDate", date);

        return "support/form"; // templates/support/form.html
    }

    // 3) ✅ 상세 화면 (id로 조회)
    // URL: /support/support/detail?id=1
    @GetMapping("/detail")
    public String detail(@RequestParam int id, Model model) {
        model.addAttribute("activeMenu", "support");

        Map<String, Object> support = dummySupports().stream()
                .filter(s -> (int) s.get("id") == id)
                .findFirst()
                .orElse(null);

        model.addAttribute("support", support);
        return "support/detail"; // templates/support/detail.html
    }
}
