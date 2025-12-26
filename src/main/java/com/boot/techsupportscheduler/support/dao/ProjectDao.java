package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.Project;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectDao {

    List<Project> doList();

    int doInsert(Project project);

    Project doDetail(Long projectId);
}
