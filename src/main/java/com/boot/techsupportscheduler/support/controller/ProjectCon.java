package com.boot.techsupportscheduler.support.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/support/project")
public class ProjectCon {

    @GetMapping("/list")
    public String list(Model model) {

        List<Map<String, Object>> projects = new ArrayList<>();

        Map<String, Object> p1 = new HashMap<>();
        p1.put("projectId", 1L);
        p1.put("projectCode", "TS-202512-8F3K2Q");
        p1.put("projectName", "OO사 유지보수");
        p1.put("clientName", "OO주식회사");
        p1.put("salesManager", "홍길동");
        p1.put("contractAmount", 15000000L);
        p1.put("status", "진행중");
        p1.put("dday", 12);
        p1.put("regDate", "2024-05-02");
        projects.add(p1);

        Map<String, Object> p2 = new HashMap<>();
        p2.put("projectId", 2L);
        p2.put("projectCode", "TS-202512-9F3K2Q");
        p2.put("projectName", "XX사 설치지원");
        p2.put("clientName", "XX주식회사");
        p2.put("salesManager", "홍길동");
        p2.put("contractAmount", 7000000L);
        p2.put("status", "대기");
        p2.put("dday", -3);
        p2.put("regDate", "2024-04-30");
        projects.add(p2);

        model.addAttribute("activeMenu", "project");
        model.addAttribute("projects", projects);
        return "project/list";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("activeMenu", "project");
        return "project/form";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("projectId") Long projectId, Model model) {
        System.out.println("detail hit projectId=" + projectId);

        model.addAttribute("activeMenu", "project");

        Map<String, Object> project = new HashMap<>();
        project.put("projectId", projectId);
        project.put("projectCode", "TS-202512-8F3K2Q");
        project.put("projectName", "OO사 유지보수");
        project.put("clientName", "OO주식회사");
        project.put("salesManager", "홍길동");
        project.put("contractAmount", 15000000L);
        project.put("contractDate", "2024-05-02");
        project.put("endDate", "2024-12-31");
        project.put("status", "진행중");

        model.addAttribute("project", project);
        return "project/detail";
    }
}
