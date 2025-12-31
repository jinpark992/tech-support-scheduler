package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.Project;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectDao {

    List<Project> doList();

    int doInsert(Project project);

    Project doDetail(Long projectId);

    void doDelete(Long projectId);

    void doUpdate(Project project);

    // ✅ 페이징용 추가 2개
    int countSearch(Map<String, Object> params);

    List<Project> searchPaged(Map<String, Object> params);
}
