package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

@Data
public class PageInfo {
    private final int page;        // 현재 페이지(1부터)
    private final int size;        // 페이지당 개수
    private final int totalCount;  // 전체 글 수
    private final int totalPages;  // 전체 페이지 수
    private final int offset;      // DB OFFSET

    // 페이징 버튼 범위(예: 1~5, 6~10)
    private final int startPage;
    private final int endPage;

    private final int blockSize = 5;

    public PageInfo(int page, int size, int totalCount) {
        int safeSize = Math.max(1, size);
        this.size = safeSize;
        this.totalCount = Math.max(0, totalCount);

        int calcTotalPages = (this.totalCount + safeSize - 1) / safeSize;
        this.totalPages = Math.max(1, calcTotalPages);

        int safePage = Math.max(1, page);
        safePage = Math.min(safePage, this.totalPages);
        this.page = safePage;

        this.offset = (this.page - 1) * this.size;

        int calcStart = ((this.page - 1) / blockSize) * blockSize + 1;
        int calcEnd = Math.min(calcStart + blockSize - 1, this.totalPages);
        this.startPage = calcStart;
        this.endPage = calcEnd;
    }

    public boolean hasPrev() {
        return page > 1;
    }

    public boolean hasNext() {
        return page < totalPages;
    }
}
