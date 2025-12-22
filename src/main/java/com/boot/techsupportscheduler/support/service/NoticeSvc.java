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

    public int doDelete(String strNo) {

        int i = noticeDao.doDelete(strNo);
        return i;
    }

    public int doInsert(Notice notice) {
        int i = noticeDao.doInsert(notice);
        return i;
    }
}
