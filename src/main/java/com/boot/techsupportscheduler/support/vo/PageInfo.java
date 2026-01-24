package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

@Data
public class PageInfo {
    private final int page;
    private final int size;
    private final int totalCount;
    private final int totalPages;
    private final int offset;
    private final int startPage;
    private final int endPage;
    private final int blockSize = 5;

    public PageInfo(int page, int size, int totalCount) {
        // 1) 최소한의 안전장치 (0으로 나누기 방지)
        this.size = Math.max(1, size);
        this.totalCount = totalCount;

        // 2) 전체 페이지 계산
        this.totalPages = Math.max(1, (totalCount + this.size - 1) / this.size);

        // 3) 현재 페이지 보정
        int safePage = Math.max(1, page);
        this.page = Math.min(safePage, this.totalPages);

        // 4) DB 건너뛰기(offset) 계산
        this.offset = (this.page - 1) * this.size;

        // 5) 화면 하단 버튼 범위 계산
        this.startPage = ((this.page - 1) / blockSize) * blockSize + 1;
        this.endPage = Math.min(this.startPage + blockSize - 1, this.totalPages);
    }

    /** 이전 페이지 번호 반환 (1보다 작아지지 않게) */
    public int prevPage() {
        return (page > 1) ? (page - 1) : 1;
    }

    /** 다음 페이지 번호 반환 (마지막 페이지보다 커지지 않게) */
    public int nextPage() {
        return (page < totalPages) ? (page + 1) : totalPages;
    }

    //에서 버튼 활성화/비활성화 판단에 사용 중
    public boolean hasPrev() { return page > 1; }
    public boolean hasNext() { return page < totalPages; }
}