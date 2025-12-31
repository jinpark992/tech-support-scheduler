package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeDao {

    List<Notice> doList();

    int doDelete(Long noticeId);

    int doInsert(Notice notice);

    Notice doDetail(Long noticeId);

    void increaseViews(Long noticeId);

    Notice doPrev(Long noticeId);

    Notice doNext(Long noticeId);

    int doUpdate(Notice notice);

    // ✅ 페이징/검색
    List<Notice> doSearch(Map<String, Object> params);

    // ✅ 페이징 계산용(전체 건수)
    int doSearchCount(Map<String, Object> params);

    int countSearch(Map<String, Object> p);

    List<Notice> searchPaged(Map<String, Object> p);
}
