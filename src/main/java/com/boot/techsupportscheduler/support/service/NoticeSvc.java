package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.NoticeDao;
import com.boot.techsupportscheduler.support.vo.Notice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class NoticeSvc {
    @Autowired
    NoticeDao noticeDao;

    public NoticeSvc() {
        log.info("NoticeSvc 생성");
    }


    public List<Notice> doList() {
        List<Notice> list = noticeDao.doList();

        log.info(list);

        return list;
    }

    public int doDelete(Long noticeId) {

        int i = noticeDao.doDelete(noticeId);
        return i;
    }

    public int doInsert(Notice notice) {
        int i = noticeDao.doInsert(notice);
        return i;
    }


    public Notice doDetail(Long noticeId) {
        noticeDao.increaseViews(noticeId); // ✅ 상세 들어가면 조회수 +1
        return noticeDao.doDetail(noticeId);
    }

    public Notice doPrev(Long noticeId) {
        Notice prev = noticeDao.doPrev(noticeId);     // 이전글
        return prev;
    }

    public Notice doNext(Long noticeId) {
        Notice next = noticeDao.doNext(noticeId);     // 다음글
        return next;
    }

    public int doUpdate(Notice notice){
        return noticeDao.doUpdate(notice);
    }
}
