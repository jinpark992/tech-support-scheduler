package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.NoticeDao;
import com.boot.techsupportscheduler.support.dao.ProjectDao;
import com.boot.techsupportscheduler.support.dao.SupportDao;
import com.boot.techsupportscheduler.support.vo.Notice;
import com.boot.techsupportscheduler.support.vo.Support;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class SupportSvc {
    @Autowired
    SupportDao supportDao;


    public List<Support> doList() {
        List<Support> list = supportDao.doList();
        return list;
    }

    public Support doDetail(Long id) {
        Support support = supportDao.doDetail(id); // ✅ DB에서 1건 조회
        return support;

    }

    public void doInsert(Support support) {
        supportDao.doInsert(support);
        return;
    }

    public void doDelete(Long id) {
        supportDao.doDelete(id);
        return;
    }
}
