package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.service.ProjectSvc;
import com.boot.techsupportscheduler.support.vo.Project;
import com.boot.techsupportscheduler.support.vo.ProjectPageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequestMapping("/support/project")
@RequiredArgsConstructor
@Log4j2
public class ProjectCon {

    private final ProjectSvc projectSvc;

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

        model.addAttribute("field", "all");
        model.addAttribute("q", r.getQ());
        model.addAttribute("status", "");
        model.addAttribute("sort", "new");

        return "project/list";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("activeMenu", "project");
        model.addAttribute("project", new Project()); // th:object="${project}" 때문에 필요
        return "project/form";
    }

    @PostMapping("/new")
    public String postNew(@ModelAttribute Project project) {
        project.setProjectCode(genProjectCode());
        projectSvc.doInsert(project);
        return "redirect:/support/project/list";
    }

    private String genProjectCode() {
        String ym = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String rand = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "TS-" + ym + "-" + rand;
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("projectId") Long projectId, Model model) {
        Project project = projectSvc.doDetail(projectId);

        model.addAttribute("activeMenu", "project");
        model.addAttribute("project", project);
        return "project/detail";
    }

    @PostMapping("/delete")
    public String doDelete(@RequestParam("projectId") Long projectId) {
        log.info("project delete projectId={}", projectId);
        projectSvc.doDelete(projectId);
        return "redirect:/support/project/list";
    }

    @GetMapping("/edit")
    public String getEdit(@RequestParam("projectId") Long projectId, Model model) {
        Project project = projectSvc.doDetail(projectId);

        model.addAttribute("activeMenu", "project");
        model.addAttribute("project", project);

        return "project/edit";
    }

    @PostMapping("/edit")
    public String postUpdate(@ModelAttribute Project project) {
        projectSvc.doUpdate(project);
        return "redirect:/support/project/detail?projectId=" + project.getProjectId();
    }

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

        model.addAttribute("field", field);
        model.addAttribute("q", r.getQ());
        model.addAttribute("status", status);
        model.addAttribute("sort", sort);

        return "project/list";
    }
}
