package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.NoticeDao;
import com.boot.techsupportscheduler.support.vo.Notice;
import com.boot.techsupportscheduler.support.vo.NoticePageResult;
import com.boot.techsupportscheduler.support.vo.PageInfo;
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
}
