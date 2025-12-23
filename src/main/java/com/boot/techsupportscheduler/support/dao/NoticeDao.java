package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {
    List<Notice> doList();

    int doDelete(Long noticeId);

    int doInsert(Notice notice);

    Notice doDetail(Long noticeId);

    void increaseViews(Long noticeId);

    Notice doPrev(Long noticeId);

    Notice doNext(Long noticeId);
}
