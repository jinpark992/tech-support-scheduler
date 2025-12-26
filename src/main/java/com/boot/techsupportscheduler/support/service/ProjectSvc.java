package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.NoticeDao;
import com.boot.techsupportscheduler.support.dao.ProjectDao;
import com.boot.techsupportscheduler.support.vo.Project;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ProjectSvc {
    @Autowired
    ProjectDao projectDao;
    public ProjectSvc() {
        log.info("NoticeSvc 생성");
    }

    public List<Project> doList() {
        List<Project> list = projectDao.doList();
        return list;
    }

    public void doInsert(Project project) {
        int i = projectDao.doInsert(project);
        return ;

    }


    public Project doDetail(Long projectId) {
        Project list = projectDao.doDetail(projectId);
        return list;
    }
}
