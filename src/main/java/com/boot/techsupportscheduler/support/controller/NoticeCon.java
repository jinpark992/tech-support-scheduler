package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.service.NoticeSvc;
import com.boot.techsupportscheduler.support.vo.Notice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/support")
@Log4j2
public class NoticeCon {
    @Autowired
    NoticeSvc noticeSvc;


 /*   // DB 아직 안 붙였으니까 더미 목록
    private final List<Notice> dummy = List.of(
            new Notice(1, true,  "기술지원 사이트 오픈 안내", "2024-05-02", 5,  "오픈 공지 내용입니다."),
            new Notice(2, false, "공지사항 테스트2",         "2024-04-30", 9,  "테스트 공지 내용입니다."),
            new Notice(3, false, "공지사항 테스트입니다",     "2024-04-28", 12, "테스트 공지 내용입니다.")
    );*/

    // 1) 목록 화면 (대시보드/공지사항 목록)
    // URL: http://localhost:8086/support/notice
    @GetMapping("/notice")
    public String list(Model model) {

        List<Notice> list = noticeSvc.doList();

        model.addAttribute("activeMenu", "home"); // 헤더 메뉴 활성화용
        model.addAttribute("notices", list);     // home.html에서 th:each로 쓰는 데이터
        return "home/home";                       // templates/home/home.html
    }

    @GetMapping("/notice/delete")
    public String doDelete(@RequestParam("noticeId") String strNo){
        log.info("noticeId : " + strNo);
        noticeSvc.doDelete(strNo);
        return "redirect:/support/notice";
    }



   // 2) 등록 폼 화면
    // URL: http://localhost:8086/support/notice/new
   @GetMapping("/notice/new")
   public String getForm(Model model) {
       model.addAttribute("activeMenu", "home");
       model.addAttribute("notice", new Notice()); // ✅ th:object="${notice}" 때문에 필수
       return "notice/form";
   }

    // 등록 처리
    @PostMapping("/notice/new")
    public String postForm(@ModelAttribute("notice") Notice notice) {

        // 폼에서 안 받는 값 기본 세팅(너 DB 컬럼 구조 기준)
        notice.setViews(0);
        notice.setDeleteYn("N");

        log.info("등록 요청 notice = {}", notice);

        noticeSvc.doInsert(notice);
        return "redirect:/support/notice";
    }
    /*
    // 3) 상세 화면 (RequestParam 방식)
    // URL: http://localhost:8086/support/notice/detail?id=1
    @GetMapping("/notice/detail")
    public String detail(@RequestParam int id, Model model) {
        model.addAttribute("activeMenu", "home");

        Notice notice = dummy.stream()
                .filter(n -> n.id() == id)
                .findFirst()
                .orElse(null);

        model.addAttribute("notice", notice); // detail.html에서 사용
        return "notice/detail"; // templates/notice/detail.html
    }*/

}
