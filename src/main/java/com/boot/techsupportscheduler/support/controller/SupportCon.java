package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.service.ProjectSvc;
import com.boot.techsupportscheduler.support.service.SupportSvc;
import com.boot.techsupportscheduler.support.vo.Project;
import com.boot.techsupportscheduler.support.vo.Support;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/support")
@RequiredArgsConstructor
@Log4j2
public class SupportCon {

    private final SupportSvc supportSvc;
    private final ProjectSvc projectSvc;

    @GetMapping("/support/list")
    public String list(Model model) {
        List<Support> list = supportSvc.doList();

        model.addAttribute("activeMenu", "support");
        model.addAttribute("supports", list);

        return "support/list";
    }

    @GetMapping("/support/detail")
    public String detail(@RequestParam("id") Long id, Model model) {
        model.addAttribute("activeMenu", "support");

        Support support = supportSvc.doDetail(id);
        if (support == null) return "redirect:/support/support/list";

        model.addAttribute("support", support);
        return "support/detail";
    }

    @GetMapping("/support/new")
    public String getForm(Model model) {
        model.addAttribute("activeMenu", "support");
        model.addAttribute("support", new Support());

        List<Project> projects = projectSvc.doList();
        model.addAttribute("projects", projects);

        List<String> salesManagers = projects.stream()
                .map(Project::getSalesManager)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .distinct()
                .toList();
        model.addAttribute("salesManagers", salesManagers);

        return "support/form";
    }

    @PostMapping("/support/new")
    public String postForm(@ModelAttribute("support") Support support) {
        if (support.getDeletedYn() == null) support.setDeletedYn("N");
        if (support.getStatus() == null || support.getStatus().isBlank()) support.setStatus("OPEN");

        log.info("support create support={}", support);
        supportSvc.doInsert(support);

        return "redirect:/support/support/list";
    }

    @PostMapping("/support/delete")
    public String doDelete(@RequestParam("id") Long id) {
        log.info("support delete ticketId={}", id);
        supportSvc.doDelete(id);
        return "redirect:/support/support/list";
    }

    @GetMapping("/support/edit")
    public String editForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("activeMenu", "support");

        Support support = supportSvc.doDetail(id);
        if (support == null) return "redirect:/support/support/list";
        model.addAttribute("support", support);

        List<Project> projects = projectSvc.doList();
        model.addAttribute("projects", projects);

        List<String> salesManagers = projects.stream()
                .map(Project::getSalesManager)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .distinct()
                .toList();
        model.addAttribute("salesManagers", salesManagers);

        return "support/edit";
    }

    @PostMapping("/support/update")
    public String doUpdate(@ModelAttribute("support") Support support) {
        if (support.getStatus() == null || support.getStatus().isBlank()) support.setStatus("OPEN");

        supportSvc.doUpdate(support);
        return "redirect:/support/support/detail?id=" + support.getTicketId();
    }
}
