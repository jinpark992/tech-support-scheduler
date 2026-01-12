package com.boot.techsupportscheduler.support.controller;

import com.boot.techsupportscheduler.support.service.NoticeSvc;
import com.boot.techsupportscheduler.support.vo.Notice;
import com.boot.techsupportscheduler.support.vo.NoticeComment;
import com.boot.techsupportscheduler.support.vo.NoticePageResult;
import com.boot.techsupportscheduler.support.vo.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/support")
@Log4j2
public class NoticeCon {

    @Autowired
    NoticeSvc noticeSvc;

    // ✅ 목록
    @GetMapping("/notice")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {

        NoticePageResult r = noticeSvc.doSearchPaged("all", null, "", "new", page, size);

        model.addAttribute("activeMenu", "home");
        model.addAttribute("notices", r.getNotices());
        model.addAttribute("pageInfo", r.getPageInfo());

        model.addAttribute("field", "all");
        model.addAttribute("q", r.getQ());
        model.addAttribute("topOnly", "");
        model.addAttribute("sort", "new");

        return "home/home";
    }

    // ✅ 등록 폼
    @GetMapping("/notice/new")
    public String getForm(Model model) {
        model.addAttribute("activeMenu", "home");
        model.addAttribute("notice", new Notice());
        return "notice/form";
    }

    // ✅ 등록 처리 (+ 파일 저장)
    // 핵심: MultipartFile[] files 받고 -> insert 후 noticeId로 저장
    @PostMapping("/notice/new")
    public String postForm(@ModelAttribute("notice") Notice notice,
                           @RequestParam(value = "files", required = false) MultipartFile[] files) {

        notice.setViews(0);
        notice.setDeleteYn("N");

        noticeSvc.doInsert(notice); // ★ 여기서 notice.noticeId가 채워져야 함 (useGeneratedKeys 설정 필요)

        // ★ 파일 저장 (DB 없이 디스크 저장)
        try {
            noticeSvc.saveFilesToDisk(notice.getNoticeId(), files);
        } catch (Exception e) {
            log.error("파일 저장 실패", e);
        }

        return "redirect:/support/notice/detail?noticeId=" + notice.getNoticeId();
    }

    // ✅ 상세 (+ 파일목록 내려줌)
    @GetMapping("/notice/detail")
    public String detail(@RequestParam("noticeId") Long noticeId, Model model) {

        Notice notice = noticeSvc.doDetail(noticeId);
        Notice prev = noticeSvc.doPrev(noticeId);
        Notice next = noticeSvc.doNext(noticeId);

        List<NoticeComment> comments = noticeSvc.getCommentList(noticeId);

        // ★ 첨부파일 목록 (저장된 파일명 리스트)
        List<String> savedNames;
        try {
            savedNames = noticeSvc.listSavedFileNames(noticeId);
        } catch (Exception e) {
            log.error("파일 목록 조회 실패", e);
            savedNames = List.of();
        }

        model.addAttribute("activeMenu", "home");
        model.addAttribute("notice", notice);
        model.addAttribute("prevNotice", prev);
        model.addAttribute("nextNotice", next);
        model.addAttribute("comments", comments);

        // ★ detail.html에서 files로 출력
        model.addAttribute("files", savedNames);

        return "notice/detail";
    }

    // ✅ 파일 다운로드 (DB 없이: noticeId + savedName 으로 다운)
    @GetMapping("/notice/file")
    @ResponseBody
    public ResponseEntity<Resource> download(@RequestParam Long noticeId,
                                             @RequestParam String name) throws Exception {

        Resource resource = noticeSvc.loadFileAsResource(noticeId, name);
        if (resource == null) return ResponseEntity.notFound().build();

        // 다운로드 파일명은 원본명으로
        String originalName = noticeSvc.extractOriginalName(name);
        String encoded = URLEncoder.encode(originalName, StandardCharsets.UTF_8).replace("+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encoded)
                .body(resource);
    }

    // ✅ 수정 폼 (+ 현재 파일목록 보여주려면 model에 files 내려주면 됨)
    @GetMapping("/notice/edit")
    public String getEdit(@RequestParam("noticeId") Long noticeId, Model model) {
        Notice notice = noticeSvc.doDetail(noticeId);

        List<String> savedNames;
        try {
            savedNames = noticeSvc.listSavedFileNames(noticeId);
        } catch (Exception e) {
            log.error("파일 목록 조회 실패", e);
            savedNames = List.of();
        }

        model.addAttribute("activeMenu", "home");
        model.addAttribute("notice", notice);
        model.addAttribute("files", savedNames); // ★ edit.html에서 출력 가능
        return "notice/edit";
    }

    // ✅ 수정 처리 (+ 파일 추가 업로드)
    // 제일 쉬운 동작: "새 파일 올리면 기존 파일은 그대로 두고 추가"
    // (교체하고 싶으면 아래 replaceFiles 체크박스 방식으로 확장 가능)
    @PostMapping("/notice/edit")
    public String postEdit(@ModelAttribute("notice") Notice notice,
                           @RequestParam(value = "files", required = false) MultipartFile[] files) {

        noticeSvc.doUpdate(notice);

        try {
            noticeSvc.saveFilesToDisk(notice.getNoticeId(), files); // ★ 수정 시에도 파일 추가 저장
        } catch (Exception e) {
            log.error("파일 저장 실패(수정)", e);
        }

        return "redirect:/support/notice/detail?noticeId=" + notice.getNoticeId();
    }

    // ---------------- 댓글/좋아요/삭제 등 기존 로직은 그대로 ----------------

    @PostMapping("/notice/delete")
    public String doDelete(@RequestParam("noticeId") Long noticeId) {
        noticeSvc.doDelete(noticeId);
        return "redirect:/support/notice";
    }

    @PostMapping("/notice/comment/add")
    public String addComment(@RequestParam Long noticeId,
                             @RequestParam String content,
                             HttpSession session) {

        SessionUser user = (SessionUser) session.getAttribute("LOGIN_USER");
        if (user == null) return "redirect:/login";

        NoticeComment c = new NoticeComment();
        c.setNoticeId(noticeId);
        c.setWriter(user.getLoginId());
        c.setContent(content);

        noticeSvc.addComment(c);
        return "redirect:/support/notice/detail?noticeId=" + noticeId;
    }

    @ResponseBody
    @PostMapping("/notice/comment/like")
    public Map<String, Object> likeComment(@RequestParam("commentId") Long commentId,
                                           HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        SessionUser user = (SessionUser) session.getAttribute("LOGIN_USER");
        if (user == null) {
            response.put("result", "LOGIN_REQUIRED");
            return response;
        }

        String resultType = noticeSvc.toggleLike(commentId, user.getLoginId());
        int newCount = noticeSvc.getLikeCount(commentId);

        response.put("result", resultType);
        response.put("count", newCount);
        return response;
    }

    @GetMapping("/notice/comment/editForm")
    public String editCommentForm(@RequestParam Long commentId,
                                  @RequestParam Long noticeId,
                                  Model model,
                                  HttpSession session) {

        SessionUser user = (SessionUser) session.getAttribute("LOGIN_USER");
        if (user == null) return "redirect:/login";

        NoticeComment c = noticeSvc.getCommentById(commentId);
        if (c == null) return "redirect:/support/notice/detail?noticeId=" + noticeId;

        boolean can = "ROLE_ADMIN".equals(user.getRole()) || user.getLoginId().equals(c.getWriter());
        if (!can) return "redirect:/support/forbidden";

        model.addAttribute("activeMenu", "home");
        model.addAttribute("noticeId", noticeId);
        model.addAttribute("comment", c);
        return "notice/comment_edit";
    }

    @PostMapping("/notice/comment/edit")
    public String editComment(@RequestParam Long commentId,
                              @RequestParam Long noticeId,
                              @RequestParam String content,
                              HttpSession session) {

        SessionUser user = (SessionUser) session.getAttribute("LOGIN_USER");
        if (user == null) return "redirect:/login";

        boolean ok = noticeSvc.editComment(commentId, content, user);
        if (!ok) return "redirect:/support/forbidden";

        return "redirect:/support/notice/detail?noticeId=" + noticeId;
    }

    @PostMapping("/notice/comment/delete")
    public String deleteComment(@RequestParam Long commentId,
                                @RequestParam Long noticeId,
                                HttpSession session) {

        SessionUser user = (SessionUser) session.getAttribute("LOGIN_USER");
        if (user == null) return "redirect:/login";

        boolean ok = noticeSvc.deleteComment(commentId, user);
        if (!ok) return "redirect:/support/forbidden";

        return "redirect:/support/notice/detail?noticeId=" + noticeId;
    }



}
