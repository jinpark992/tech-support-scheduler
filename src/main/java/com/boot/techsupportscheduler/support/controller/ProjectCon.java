package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.dao.NoticeDao;
import com.boot.techsupportscheduler.support.service.NoticeSvc;
import com.boot.techsupportscheduler.support.service.ProjectSvc;
import com.boot.techsupportscheduler.support.vo.Notice;
import com.boot.techsupportscheduler.support.vo.Project;
import com.boot.techsupportscheduler.support.vo.ProjectPageResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@Log4j2
@RequestMapping("/support/project")
public class ProjectCon {
    @Autowired
    ProjectSvc projectSvc;

    // ✅ 목록(기본) - GET
    @GetMapping("/list")
    public String list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        ProjectPageResult r = projectSvc.doSearchPaged("all", null, "", "new", page, size);

        model.addAttribute("activeMenu", "project");
        model.addAttribute("projects", r.getProjects());
        model.addAttribute("pageInfo", r.getPageInfo());

        // ✅ 화면 선택값 유지(기본값)
        model.addAttribute("field", "all");
        model.addAttribute("q", r.getQ());
        model.addAttribute("status", "");
        model.addAttribute("sort", "new");

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
    @PostMapping("/delete")
    public String doDelete(@RequestParam("projectId") Long projectId){
        log.info("noticeId : " + projectId);
        projectSvc.doDelete(projectId);
        return "redirect:/support/project/list";
    }

    @GetMapping("/edit")
    public String getEdit(@RequestParam("projectId") Long projectId, Model model) {

        Project project = projectSvc.doDetail(projectId);

        model.addAttribute("activeMenu", "project");
        model.addAttribute("project", project);

        return "project/edit"; // templates/support/project/project-edit.html
    }

    @PostMapping("/edit")
    public String postUpdate(@ModelAttribute Project project) {

        projectSvc.doUpdate(project);

        return "redirect:/support/project/detail?projectId=" + project.getProjectId();
    }


    // ✅ 검색(POST 유지) + 페이징(POST로 페이지 이동)
    @PostMapping("/search")
    public String search(
            @RequestParam(defaultValue = "all") String field,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        ProjectPageResult r = projectSvc.doSearchPaged(field, q, status, sort, page, size);

        model.addAttribute("activeMenu", "project");
        model.addAttribute("projects", r.getProjects());
        model.addAttribute("pageInfo", r.getPageInfo());

        // ✅ 화면 선택값 유지
        model.addAttribute("field", field);
        model.addAttribute("q", r.getQ());
        model.addAttribute("status", status);
        model.addAttribute("sort", sort);

        return "project/list";
    }



}
