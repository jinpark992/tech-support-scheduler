package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.SupportDao;
import com.boot.techsupportscheduler.support.vo.Support;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportSvc {

    private final SupportDao supportDao;

    public List<Support> doList() {
        return supportDao.doList();
    }

    public Support doDetail(Long id) {
        return supportDao.doDetail(id);
    }

    public void doInsert(Support support) {
        supportDao.doInsert(support);
    }

    public void doDelete(Long id) {
        supportDao.doDelete(id);
    }

    public void doUpdate(Support support) {
        supportDao.doUpdate(support);
    }
}
