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

    /** * [공지사항 컨트롤러]
     * 주요 기능: 목록 조회(페이징/검색), 등록/수정/삭제, 파일 업로드/다운로드, 댓글 관리
     */

    /** [GET] 공지사항 메인 목록: 처음 페이지 진입 시 기본값(1페이지, 10개씩, 최신순)으로 데이터 로드 */
    @GetMapping("/notice")
    public String list(@RequestParam(defaultValue = "1") int page,  // page 1 기준
                       @RequestParam(defaultValue = "10") int size, // size (글의 갯수) 10 기준
                       Model model) {

        // NoticePageResult: 검색 결과 목록과 페이징 정보를 하나의 객체로 묶어 반환함으로써 유지보수성과 타입 안정성을 높임
        // 서비스에서 검색 조건 없이 기본 페이징 처리된 결과 가져오기
        NoticePageResult r = noticeSvc.doSearchPaged("all", null, "", "new", page, size);

        // 서비스에서 검색 조건 없이 기본 페이징 처리된 결과 가져오기
        model.addAttribute("activeMenu", "home");
        model.addAttribute("notices", r.getNotices());
        model.addAttribute("pageInfo", r.getPageInfo());

        // 검색 상태 유지를 위한 파라미터 전달
        model.addAttribute("field", "all");
        model.addAttribute("q", r.getQ());
        model.addAttribute("topOnly", "");
        model.addAttribute("sort", "new");

        return "home/home";
    }

    /** [POST] 공지사항 검색: 사용자가 선택한 조건(필드, 키워드, 정렬 등)을 기반으로 목록 재조회 */
    @PostMapping("/notice_search")
    public String search(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(defaultValue = "all") String field,
                         @RequestParam(required = false) String q,
                         @RequestParam(defaultValue = "") String topOnly,
                         @RequestParam(defaultValue = "new") String sort,
                         Model model) {

        // 사용자가 보낸 검색/페이징 조건으로 데이터 재조회
        NoticePageResult r = noticeSvc.doSearchPaged(field, q, topOnly, sort, page, size);

        model.addAttribute("activeMenu", "home");
        model.addAttribute("notices", r.getNotices());
        model.addAttribute("pageInfo", r.getPageInfo());

        // 검색어와 선택 조건을 화면에 그대로 유지시키기 위해 다시 담음
        model.addAttribute("field", field);
        model.addAttribute("q", r.getQ());
        model.addAttribute("topOnly", topOnly);
        model.addAttribute("sort", sort);

        return "home/home";
    }

    /** [GET] 글 쓰기 폼 호출 */
    @GetMapping("/notice/new")
    public String getForm(Model model) {
        model.addAttribute("activeMenu", "home");
        model.addAttribute("notice", new Notice());
        return "notice/form";
    }

    /** [POST] 글 등록 처리: 데이터 DB 저장 및 첨부파일 디스크 저장 */
    // 핵심: MultipartFile[] files 받고 -> insert 후 noticeId로 저장
    @PostMapping("/notice/new")
    public String postForm(@ModelAttribute("notice") Notice notice,
                           @RequestParam(value = "files", required = false) MultipartFile[] files) {

        notice.setViews(0); // 초기 조회수 0
        notice.setDeleteYn("N"); // 삭제 상태 아님

        // DB에 텍스트 데이터 먼저 저장 (생성된 noticeId가 객체에 담겨 돌아와야 함)
        noticeSvc.doInsert(notice); // ★ 여기서 notice.noticeId가 채워져야 함 (useGeneratedKeys 설정 필요)

        // 업로드된 파일이 있다면 해당 noticeId 폴더를 만들어 디스크에 저장
        try {
            noticeSvc.saveFilesToDisk(notice.getNoticeId(), files);
        } catch (Exception e) {
            log.error("파일 저장 실패", e);
        }

        return "redirect:/support/notice/detail?noticeId=" + notice.getNoticeId();
    }

    /** [GET] 글 상세 보기: 본문 내용, 이전/다음 글 정보, 댓글 목록, 파일 목록 로드 */
    @GetMapping("/notice/detail")
    public String detail(@RequestParam("noticeId") Long noticeId, Model model) {

        Notice notice = noticeSvc.doDetail(noticeId); // 본문 및 조회수 증가
        Notice prev = noticeSvc.doPrev(noticeId);   // 이전 글 정보
        Notice next = noticeSvc.doNext(noticeId); // 다음 글 정보

        List<NoticeComment> comments = noticeSvc.getCommentList(noticeId); // 댓글 목록

        // 해당 게시글에 저장된 파일명 리스트 조회
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
        model.addAttribute("files", savedNames); // 화면에서 다운로드 링크 생성용

        return "notice/detail";
    }

    /** [GET] 파일 다운로드 처리: noticeId와 파일명을 받아 실제 리소스 반환 */
    @GetMapping("/notice/file")
    @ResponseBody
    public ResponseEntity<Resource> download(@RequestParam Long noticeId,
                                             @RequestParam String name) throws Exception {

        Resource resource = noticeSvc.loadFileAsResource(noticeId, name);
        if (resource == null) return ResponseEntity.notFound().build();

        // 한글 파일명 깨짐 방지를 위한 인코딩 처리
        String originalName = noticeSvc.extractOriginalName(name);
        String encoded = URLEncoder.encode(originalName, StandardCharsets.UTF_8).replace("+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encoded)
                .body(resource);
    }

    /** [GET] 글 수정 폼 호출: 기존 데이터와 저장된 파일 목록 로드 */
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

    /** [POST] 글 수정 처리: DB 업데이트 및 추가된 파일 저장 */
    @PostMapping("/notice/edit")
    public String postEdit(@ModelAttribute("notice") Notice notice,
                           @RequestParam(value = "files", required = false) MultipartFile[] files) {

        noticeSvc.doUpdate(notice);

        try {
            noticeSvc.saveFilesToDisk(notice.getNoticeId(), files); // 기존 파일 유지, 새 파일 추가
        } catch (Exception e) {
            log.error("파일 저장 실패(수정)", e);
        }

        return "redirect:/support/notice/detail?noticeId=" + notice.getNoticeId();
    }

    /** [POST] 글 삭제 처리: 논리적 삭제(deleted_yn = 'Y') 수행 */
    @PostMapping("/notice/delete")
    public String doDelete(@RequestParam("noticeId") Long noticeId) {
        noticeSvc.doDelete(noticeId);
        return "redirect:/support/notice";
    }

    // ---------------------------------------------------------
    // 3. 댓글 및 좋아요 영역
    //

    /** [POST] 댓글 추가 */
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

    /** [POST/AJAX] 댓글 좋아요 토글: 좋아요 클릭 시 추가/취소 처리 및 현재 카운트 반환 */
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

    /** [GET] 댓글 수정 폼 호출: 작성자 본인 또는 관리자만 접근 가능 */
    @GetMapping("/notice/comment/editForm")
    public String editCommentForm(@RequestParam Long commentId,
                                  @RequestParam Long noticeId,
                                  Model model,
                                  HttpSession session) {

        SessionUser user = (SessionUser) session.getAttribute("LOGIN_USER");
        if (user == null) return "redirect:/login";

        NoticeComment c = noticeSvc.getCommentById(commentId);
        if (c == null) return "redirect:/support/notice/detail?noticeId=" + noticeId;

        // 권한 체크: 관리자이거나 작성자 본인이어야 함
        boolean can = "ROLE_ADMIN".equals(user.getRole()) || user.getLoginId().equals(c.getWriter());
        if (!can) return "redirect:/support/forbidden";

        model.addAttribute("activeMenu", "home");
        model.addAttribute("noticeId", noticeId);
        model.addAttribute("comment", c);
        return "notice/comment_edit";
    }

    /** [POST] 댓글 수정 실행 */
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

    /** [POST] 댓글 삭제 실행 */
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
