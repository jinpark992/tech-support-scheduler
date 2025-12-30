package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.dao.SupportDao;
import com.boot.techsupportscheduler.support.service.NoticeSvc;
import com.boot.techsupportscheduler.support.service.ProjectSvc;
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
    @Autowired
    ProjectSvc projectSvc; // ✅ 필요 (프로젝트 드롭다운)


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



    // 2) 등록 폼 화면
    // URL: http://localhost:8086/support/notice/new
    @GetMapping("/support/new")
    public String getForm(Model model) {
        model.addAttribute("activeMenu", "support");
        model.addAttribute("support", new Support());
        model.addAttribute("projects", projectSvc.doList());
        return "support/form";
    }


    // ✅ 등록 처리
    @PostMapping("/support/new")
    public String postForm(@ModelAttribute("support") Support support, Model model) {

        // Support 기본값 세팅(너 테이블 컬럼 기준에 맞춰서)
        if (support.getDeletedYn() == null) support.setDeletedYn("N");
        if (support.getStatus() == null || support.getStatus().isBlank()) support.setStatus("OPEN");

        log.info("등록 요청 support = {}", support);

        supportSvc.doInsert(support);

        return "redirect:/support/support/list"; // ✅ 경로 맞추기s
    }
}
