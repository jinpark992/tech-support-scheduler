package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.NoticeDao;
import com.boot.techsupportscheduler.support.vo.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class NoticeSvc {

    @Autowired
    NoticeDao noticeDao;

    public NoticeSvc() {
        log.info("NoticeSvc 생성");
    }

    public List<Notice> doList() {
        return noticeDao.doList();
    }

    public int doDelete(Long noticeId) {
        return noticeDao.doDelete(noticeId);
    }

    public int doInsert(Notice notice) {
        return noticeDao.doInsert(notice);
    }

    public Notice doDetail(Long noticeId) {
        noticeDao.increaseViews(noticeId);
        return noticeDao.doDetail(noticeId);
    }

    public Notice doPrev(Long noticeId) {
        return noticeDao.doPrev(noticeId);
    }

    public Notice doNext(Long noticeId) {
        return noticeDao.doNext(noticeId);
    }

    public int doUpdate(Notice notice) {
        return noticeDao.doUpdate(notice);
    }

    public NoticePageResult doSearchPaged(String field, String q, String topOnly, String sort, int page, int size) {

        // 검색어 정리(공백 제거 + 공백만이면 null 처리)
        if (q != null) q = q.trim();
        if (q != null && q.isBlank()) q = null;

        // 1) COUNT용 파라미터(검색/상단 조건 동일해야 함)
        // Dao에서 key값 설정을 위해 사용.
        Map<String, Object> p = new HashMap<>();
        p.put("field", field);
        p.put("q", q);
        p.put("topOnly", topOnly);
        p.put("sort", sort);

        //  2) 전체 글 수 먼저 구함 (이게 있어야 총페이지 계산 가능)
        int totalCount = noticeDao.countSearch(p);

        // 3) PageInfo가 totalPages, offset 계산
        PageInfo pageInfo = new PageInfo(page, size, totalCount);

        //  4) PageInfo의 size 와 offset(DB에서 가져올 때 앞부분에서 건너뛸 데이터의 개수)을 넘겨주기 위한 서비스
        p.put("size", pageInfo.getSize());
        p.put("offset", pageInfo.getOffset());

        List<Notice> notices = noticeDao.searchPaged(p);

        //  5) 화면으로 보낼 “결과 박스” 리턴
        return new NoticePageResult(notices, pageInfo, q);
    }

    public List<NoticeComment> getCommentList(Long noticeId) {
        return noticeDao.selectCommentList(noticeId);
    }

    // 2. 댓글 등록
    public void addComment(NoticeComment comment) {
        noticeDao.insertComment(comment);
    }

    // 3. 좋아요 처리 (증가시키고 -> 최신값 가져오기)
    public int likeComment(Long commentId) {
        noticeDao.increaseLikeCount(commentId); // 카운트 +1
        return noticeDao.selectLikeCount(commentId); // 증가된 값 조회
    }

    // [좋아요 토글 기능]
    // 리턴값: "LIKE"(좋아요 성공), "UNLIKE"(취소 성공)
    public String toggleLike(Long commentId, String loginId) { // 파라미터 이름: loginId

        // 1. "박 기사(DAO), 장부 좀 봐봐. 이 사람이 이 댓글에 이미 눌렀어?"
        int count = noticeDao.checkLikeHistory(commentId, loginId);

        if (count > 0) {
            // [CASE A] 이미 눌렀음 -> "취소하고 싶구나!"
            noticeDao.deleteLikeHistory(commentId, loginId); // 여기도 loginId
            noticeDao.decreaseLikeCount(commentId);
            return "UNLIKE";

        } else {
            // [CASE B] 안 눌렀음 -> "좋아요하고 싶구나!"
            noticeDao.insertLikeHistory(commentId, loginId); // 여기도 loginId
            noticeDao.increaseLikeCount(commentId);
            return "LIKE";
        }
    }

    // [조회] 현재 좋아요 개수 가져오기
    public int getLikeCount(Long commentId) {
        return noticeDao.selectLikeCount(commentId);
    }

    public NoticeComment getCommentById(Long commentId) {
        return noticeDao.selectCommentById(commentId);
    }

    public boolean editComment(Long commentId, String content, SessionUser user) {
        NoticeComment c = noticeDao.selectCommentById(commentId);
        if (c == null) return false;

        boolean can = "ROLE_ADMIN".equals(user.getRole()) || user.getLoginId().equals(c.getWriter());
        if (!can) return false;

        noticeDao.updateCommentContent(commentId, content);
        return true;
    }

    public boolean deleteComment(Long commentId, SessionUser user) {
        NoticeComment c = noticeDao.selectCommentById(commentId);
        if (c == null) return false;

        boolean can = "ROLE_ADMIN".equals(user.getRole()) || user.getLoginId().equals(c.getWriter());
        if (!can) return false;

        noticeDao.softDeleteComment(commentId);
        return true;
    }

    // NoticeSvc 클래스 안에 넣어라.


    public void saveFilesToDisk(Long noticeId, MultipartFile[] files) throws Exception {
        // files가 없으면 아무것도 안 함
        if (noticeId == null || files == null || files.length == 0) return;

        // 저장 폴더: 사용자홈/notice_upload/공지번호
        Path dir = Paths.get(System.getProperty("user.home"), "notice_upload", String.valueOf(noticeId));
        Files.createDirectories(dir);

        for (MultipartFile f : files) {
            if (f == null || f.isEmpty()) continue;

            String original = Optional.ofNullable(f.getOriginalFilename()).orElse("file");
            original = original.replaceAll("[\\\\/]", "_").replaceAll("\\s+", "_"); // 경로/공백 방지

            String saveName = UUID.randomUUID() + "__" + original; // 저장명: UUID__원본명
            f.transferTo(dir.resolve(saveName).toFile());
        }
    }

    public List<String> listSavedFileNames(Long noticeId) throws Exception {
        Path dir = Paths.get(System.getProperty("user.home"), "notice_upload", String.valueOf(noticeId));
        if (!Files.exists(dir)) return List.of();

        try (Stream<Path> stream = Files.list(dir)) {
            return stream.filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    public Resource loadFileAsResource(Long noticeId, String name) throws Exception {
        Path path = Paths.get(System.getProperty("user.home"), "notice_upload", String.valueOf(noticeId), name);
        if (!Files.exists(path)) return null;
        return new UrlResource(path.toUri());
    }

    public String extractOriginalName(String savedName) {
        int idx = savedName.indexOf("__");
        return (idx >= 0) ? savedName.substring(idx + 2) : savedName;
    }

}
