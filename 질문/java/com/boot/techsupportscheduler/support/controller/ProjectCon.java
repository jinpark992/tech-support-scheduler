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
        p1.put("id", 1);
        p1.put("name", "OO사 유지보수");
        p1.put("client", "OO주식회사");
        p1.put("amount", "15,000,000");
        p1.put("status", "진행중");
        p1.put("regDate", "2024-05-02");
        projects.add(p1);

        Map<String, Object> p2 = new HashMap<>();
        p2.put("id", 2);
        p2.put("name", "XX사 설치지원");
        p2.put("client", "XX주식회사");
        p2.put("amount", "7,000,000");
        p2.put("status", "대기");
        p2.put("regDate", "2024-04-30");
        projects.add(p2);

        model.addAttribute("activeMenu", "project");
        model.addAttribute("projects", projects);
        return "project/list";
    }

    @GetMapping("/new")
    public String form(Model model){
        model.addAttribute("activeMenu", "project");
        return "project/form";
    }
}
