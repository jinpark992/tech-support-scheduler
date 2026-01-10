package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.NoticeDao;
import com.boot.techsupportscheduler.support.vo.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // ✅ 검색어 정리(공백 제거 + 공백만이면 null 처리)
        if (q != null) q = q.trim();
        if (q != null && q.isBlank()) q = null;

        // ✅ 1) COUNT용 파라미터(검색/상단 조건 동일해야 함)
        Map<String, Object> p = new HashMap<>();
        p.put("field", field);
        p.put("q", q);
        p.put("topOnly", topOnly);
        p.put("sort", sort);

        // ✅ 2) 전체 글 수 먼저 구함 (이게 있어야 총페이지 계산 가능)
        int totalCount = noticeDao.countSearch(p);

        // ✅ 3) PageInfo가 totalPages, offset 계산
        PageInfo pageInfo = new PageInfo(page, size, totalCount);

        // ✅ 4) 목록 조회용 파라미터에 LIMIT/OFFSET 추가
        p.put("size", pageInfo.getSize());
        p.put("offset", pageInfo.getOffset());

        List<Notice> notices = noticeDao.searchPaged(p);

        // ✅ 5) 화면으로 보낼 “결과 박스” 리턴
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

}
