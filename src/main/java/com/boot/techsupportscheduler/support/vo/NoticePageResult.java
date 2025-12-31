package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

import java.util.List;

@Data
public class NoticePageResult {
    private final List<Notice> notices;
    private final PageInfo pageInfo;
    private final String q; // 컨트롤러에서 r.getQ() 쓰고 있으니 맞춰둠

    public NoticePageResult(List<Notice> notices, PageInfo pageInfo, String q) {
        this.notices = notices;
        this.pageInfo = pageInfo;
        this.q = q;
    }
}
