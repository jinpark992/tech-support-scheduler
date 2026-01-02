package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;   // ✅ 추가

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectDao {

    List<Project> doList();

    int doInsert(Project project);

    Project doDetail(@Param("projectId") Long projectId); // ✅ 수정

    void doDelete(@Param("projectId") Long projectId);    // ✅ 수정

    void doUpdate(Project project);

    int countSearch(Map<String, Object> params);
    List<Project> searchPaged(Map<String, Object> params);
}
