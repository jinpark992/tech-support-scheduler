package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.ProjectDao;
import com.boot.techsupportscheduler.support.vo.PageInfo;
import com.boot.techsupportscheduler.support.vo.Project;
import com.boot.techsupportscheduler.support.vo.ProjectPageResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class ProjectSvc {

    @Autowired
    ProjectDao projectDao;

    public List<Project> doList() {
        return projectDao.doList();
    }

    public int doInsert(Project project) {
        return projectDao.doInsert(project);
    }

    public Project doDetail(Long projectId) {
        return projectDao.doDetail(projectId);
    }

    public void doDelete(Long projectId) {
        projectDao.doDelete(projectId);
    }

    public void doUpdate(Project project) {
        projectDao.doUpdate(project);
    }

    // ✅ 검색 + 페이징 핵심
    public ProjectPageResult doSearchPaged(String field, String q, String status, String sort, int page, int size) {

        // 1) 검색어 정리
        if (q != null) q = q.trim();
        if (q != null && q.isBlank()) q = null;

        Map<String, Object> p = new HashMap<>();
        p.put("field", field);
        p.put("q", q);
        p.put("status", status);
        p.put("sort", sort);

        // 2) 전체 건수
        int totalCount = projectDao.countSearch(p);

        // 3) PageInfo가 offset 계산해줌
        PageInfo pageInfo = new PageInfo(page, size, totalCount);

        // 4) LIMIT/OFFSET 파라미터 추가
        p.put("size", pageInfo.getSize());
        p.put("offset", pageInfo.getOffset());

        // 5) 현재 페이지 데이터만 조회
        List<Project> list = projectDao.searchPaged(p);

        return new ProjectPageResult(list, pageInfo, q);
    }
}
