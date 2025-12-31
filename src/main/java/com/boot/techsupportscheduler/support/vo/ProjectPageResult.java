package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProjectPageResult {
    private final List<Project> projects;
    private final PageInfo pageInfo;
    private final String q; // 화면 유지용

    public ProjectPageResult(List<Project> projects, PageInfo pageInfo, String q) {
        this.projects = projects;
        this.pageInfo = pageInfo;
        this.q = q;
    }
}
