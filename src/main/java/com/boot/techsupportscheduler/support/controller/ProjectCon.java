package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.dao.NoticeDao;
import com.boot.techsupportscheduler.support.service.NoticeSvc;
import com.boot.techsupportscheduler.support.service.ProjectSvc;
import com.boot.techsupportscheduler.support.vo.Notice;
import com.boot.techsupportscheduler.support.vo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/support/project")
public class ProjectCon {
    @Autowired
    ProjectSvc projectSvc;

    @GetMapping("/list")
    public String list(Model model) {

        List<Project> list = projectSvc.doList();

//        Map<String, Object> p1 = new HashMap<>();
//        p1.put("projectId", 1L);
//        p1.put("projectCode", "TS-202512-8F3K2Q");
//        p1.put("projectName", "OO사 유지보수");
//        p1.put("clientName", "OO주식회사");
//        p1.put("salesManager", "홍길동");
//        p1.put("contractAmount", 15000000L);
//        p1.put("status", "진행중");
//        p1.put("dday", 12);
//        p1.put("regDate", "2024-05-02");
//        projects.add(p1);
//
//        Map<String, Object> p2 = new HashMap<>();
//        p2.put("projectId", 2L);
//        p2.put("projectCode", "TS-202512-9F3K2Q");
//        p2.put("projectName", "XX사 설치지원");
//        p2.put("clientName", "XX주식회사");
//        p2.put("salesManager", "홍길동");
//        p2.put("contractAmount", 7000000L);
//        p2.put("status", "대기");
//        p2.put("dday", -3);
//        p2.put("regDate", "2024-04-30");
//        projects.add(p2);

        model.addAttribute("activeMenu", "project");
        model.addAttribute("projects", list);
        return "project/list";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("activeMenu", "project");
        model.addAttribute("project", new Project()); // ✅ th:object="${project}" 때문에 필수

        return "project/form";
    }

    @PostMapping("/new")
    public String postNew(@ModelAttribute Project project) {
        project.setProjectCode(genProjectCode());   // 여기서 랜덤 생성
        projectSvc.doInsert(project);
        return "redirect:/support/project/list";
    }

    private String genProjectCode() {
        String ym = java.time.LocalDate.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMM"));
        String rand = java.util.UUID.randomUUID().toString()
                .replace("-", "").substring(0, 8).toUpperCase();
        return "TS-" + ym + "-" + rand;
    }



    @GetMapping("/detail")
    public String detail(@RequestParam("projectId") Long projectId, Model model) {
        System.out.println("detail hit projectId=" + projectId);
        Project list = projectSvc.doDetail(projectId);

///        Map<String, Object> project = new HashMap<>();
//        project.put("projectId", projectId);
//        project.put("projectCode", "TS-202512-8F3K2Q");
//        project.put("projectName", "OO사 유지보수");
//        project.put("clientName", "OO주식회사");
//        project.put("salesManager", "홍길동");
//        project.put("contractAmount", 15000000L);
//        project.put("contractDate", "2024-05-02");
//        project.put("endDate", "2024-12-31");
//        project.put("status", "진행중");
//        project.put("memo", "메모");

        model.addAttribute("activeMenu", "project");
        model.addAttribute("project", list);
        return "project/detail";
    }
}
