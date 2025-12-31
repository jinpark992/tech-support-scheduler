package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.service.NoticeSvc;
import com.boot.techsupportscheduler.support.vo.Notice;
import com.boot.techsupportscheduler.support.vo.NoticePageResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/support")
@Log4j2
public class NoticeCon {

    @Autowired
    NoticeSvc noticeSvc;

    // ✅ 목록 화면 (대시보드/공지사항 목록) - 페이징 포함
    // URL: http://localhost:8088/support/notice
    @GetMapping("/notice")
    public String list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        // ✅ 처음 들어올 땐 “기본값(검색 없음)”으로 페이징 조회
        NoticePageResult r = noticeSvc.doSearchPaged("all", null, "", "new", page, size);

        model.addAttribute("activeMenu", "home");
        model.addAttribute("notices", r.getNotices());
        model.addAttribute("pageInfo", r.getPageInfo());

        // ✅ 화면 선택값 유지(기본값)
        model.addAttribute("field", "all");
        model.addAttribute("q", r.getQ()); // null일 수 있음
        model.addAttribute("topOnly", "");
        model.addAttribute("sort", "new");

        return "home/home";
    }

    @PostMapping("/notice/delete")
    public String doDelete(@RequestParam("noticeId") Long noticeId) {
        log.info("noticeId : {}", noticeId);
        noticeSvc.doDelete(noticeId);
        return "redirect:/support/notice";
    }

    // ✅ 등록 폼 화면
    @GetMapping("/notice/new")
    public String getForm(Model model) {
        model.addAttribute("activeMenu", "home");
        model.addAttribute("notice", new Notice()); // th:object="${notice}" 때문에 필요
        return "notice/form";
    }

    // ✅ 등록 처리
    @PostMapping("/notice/new")
    public String postForm(@ModelAttribute("notice") Notice notice) {
        notice.setViews(0);
        notice.setDeleteYn("N");

        log.info("등록 요청 notice = {}", notice);
        noticeSvc.doInsert(notice);

        return "redirect:/support/notice";
    }

    // ✅ 상세 화면
    @GetMapping("/notice/detail")
    public String detail(@RequestParam("noticeId") Long noticeId, Model model) {
        Notice notice = noticeSvc.doDetail(noticeId);
        Notice prev = noticeSvc.doPrev(noticeId);
        Notice next = noticeSvc.doNext(noticeId);

        model.addAttribute("activeMenu", "home");
        model.addAttribute("notice", notice);
        model.addAttribute("prevNotice", prev);
        model.addAttribute("nextNotice", next);

        return "notice/detail";
    }

    // ✅ 수정 폼
    @GetMapping("/notice/edit")
    public String getEdit(@RequestParam("noticeId") Long noticeId, Model model) {
        Notice notice = noticeSvc.doDetail(noticeId);

        model.addAttribute("activeMenu", "home");
        model.addAttribute("notice", notice);

        return "notice/edit";
    }

    // ✅ 수정 처리
    @PostMapping("/notice/edit")
    public String postEdit(@ModelAttribute("notice") Notice notice) {
        noticeSvc.doUpdate(notice);
        return "redirect:/support/notice/detail?noticeId=" + notice.getNoticeId();
    }

    // ✅ 검색(POST 유지) + 페이징
    @PostMapping("/notice_search")
    public String noticeSearch(
            @RequestParam(defaultValue = "all") String field,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String topOnly,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        // ✅ 검색 조건을 포함해서 페이징 조회
        NoticePageResult r = noticeSvc.doSearchPaged(field, q, topOnly, sort, page, size);

        model.addAttribute("activeMenu", "home");
        model.addAttribute("notices", r.getNotices());
        model.addAttribute("pageInfo", r.getPageInfo());

        // ✅ 화면 선택값 유지(사용자 선택값)
        model.addAttribute("field", field);
        model.addAttribute("q", r.getQ());
        model.addAttribute("topOnly", topOnly);
        model.addAttribute("sort", sort);

        return "home/home";
    }
}
