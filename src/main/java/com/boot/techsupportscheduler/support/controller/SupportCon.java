package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.dao.SupportDao;
import com.boot.techsupportscheduler.support.service.NoticeSvc;
import com.boot.techsupportscheduler.support.service.SupportSvc;
import com.boot.techsupportscheduler.support.vo.Notice;
import com.boot.techsupportscheduler.support.vo.Support;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/support")
@Log4j2
public class SupportCon {
    @Autowired
    SupportSvc supportSvc;

   /* private List<Map<String, Object>> dummySupports() {
        List<Map<String, Object>> supports = new ArrayList<>();

        Map<String, Object> s1 = new HashMap<>();
        s1.put("id", 1);
        s1.put("date", "2025-12-19");
        s1.put("projectId", 1);
        s1.put("projectName", "OO사 유지보수");
        s1.put("type", "장애지원");
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
    }*/

    @GetMapping("/support/list")
    public String list(Model model) {
        List<Support> list = supportSvc.doList();

        model.addAttribute("activeMenu", "support");
        model.addAttribute("supports", list);   // ✅ list.html에서 SUPPORTS로 쓰는 그 변수

        return "support/list";                 // ✅ redirect 아님
    }

    // URL: /support/support/detail?id=1
    @GetMapping("/support/detail")
    public String detail(@RequestParam("id") Long id, Model model) {
        model.addAttribute("activeMenu", "support");

        Support support = supportSvc.doDetail(id); // ✅ DB에서 1건 조회

        if (support == null) {
            return "redirect:/support/support/list";
        }

        model.addAttribute("support", support);
        return "support/detail";
    }



    // ✅ 기술지원 등록 폼
    // URL: /support/support/new?date=2025-12-19
    @GetMapping("/support/new")
    public String form(@RequestParam(required = false) String date, Model model) {
        model.addAttribute("activeMenu", "support");
        model.addAttribute("supportTypes", List.of("유지보수", "설치지원", "장애지원"));

        // 템플릿에서 ${selectedDate}로 쓰면 됨
        model.addAttribute("selectedDate", date);

        return "support/form";
    }
}
