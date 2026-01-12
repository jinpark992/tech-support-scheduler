package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

/**
 * 페이징 계산 결과를 담는 VO
 * - "현재 페이지", "한 페이지에 몇 개", "총 글 수", "총 페이지 수"
 * - DB에서 몇 번째부터 가져올지(offset)
 * - 화면에 보여줄 페이지 버튼 범위(startPage~endPage)
 */
@Data
public class PageInfo {

    /** 현재 페이지 번호 (1부터 시작) */
    private final int page;

    /** 한 페이지에 보여줄 글 개수 */
    private final int size;

    /** 전체 글 수 */
    private final int totalCount;

    /** 전체 페이지 수 */
    private final int totalPages;

    /** DB에서 몇 개를 건너뛸지 (LIMIT/OFFSET의 OFFSET 값) */
    private final int offset;

    /** 페이지 버튼 시작 번호 (예: 1~5면 startPage=1) */
    private final int startPage;

    /** 페이지 버튼 끝 번호 (예: 1~5면 endPage=5) */
    private final int endPage;

    /**
     * 페이지 버튼을 몇 개씩 묶어서 보여줄지
     * 예: blockSize=5면 버튼이 1~5, 6~10, 11~15 이런 식으로 끊김
     */
    private final int blockSize = 5;

    /**
     * PageInfo 생성자: 입력값(page/size/totalCount)을 받아서
     * totalPages, offset, startPage, endPage까지 한 번에 계산해줌
     */
    public PageInfo(int page, int size, int totalCount) {

        // -----------------------------
        // 1) size를 안전하게 보정
        // size가 0이나 음수면 말이 안 되므로 최소 1로 강제
        // -----------------------------
        int safeSize = Math.max(1, size);
        this.size = safeSize;

        // -----------------------------
        // 2) totalCount를 안전하게 보정
        // totalCount가 음수면 말이 안 되므로 최소 0으로 강제
        // -----------------------------
        this.totalCount = Math.max(0, totalCount);

        // -----------------------------
        // 3) totalPages 계산 (나눗셈 올림)
        // 예) totalCount=123, size=10 -> 13페이지 필요
        //
        // (totalCount + size - 1) / size 는 올림 처리 공식
        // -----------------------------
        int calcTotalPages = (this.totalCount + safeSize - 1) / safeSize;

        // 글이 0개라도 페이지 버튼/화면이 깨지지 않게 최소 1페이지로 보정
        this.totalPages = Math.max(1, calcTotalPages);

        // -----------------------------
        // 4) page를 안전하게 보정
        // page가 0 이하 -> 1로 올림
        // page가 totalPages보다 큼 -> 마지막 페이지로 내림
        // -----------------------------
        int safePage = Math.max(1, page);
        safePage = Math.min(safePage, this.totalPages);
        this.page = safePage;

        // -----------------------------
        // 5) offset 계산
        // DB에서 몇 개를 건너뛰고 가져올지
        //
        // 예) page=1 -> (1-1)*size = 0  (안 건너뜀)
        // 예) page=2 -> (2-1)*size = 10 (10개 건너뜀)
        // 예) page=3 -> (3-1)*size = 20 (20개 건너뜀)
        // -----------------------------
        this.offset = (this.page - 1) * this.size;

        // -----------------------------
        // 6) 페이지 버튼 범위 계산 (startPage ~ endPage)
        //
        // blockSize=5일 때:
        // page=1~5   -> start=1,  end=5
        // page=6~10  -> start=6,  end=10
        // page=11~15 -> start=11, end=15
        // -----------------------------

        // 현재 page가 속한 "버튼 묶음"의 시작 페이지 계산
        int calcStart = ((this.page - 1) / blockSize) * blockSize + 1;

        // 끝 페이지는 시작 + blockSize - 1 이지만, totalPages를 넘기면 잘라냄
        int calcEnd = Math.min(calcStart + blockSize - 1, this.totalPages);

        this.startPage = calcStart;
        this.endPage = calcEnd;
    }

    /** 이전 페이지가 존재하는지? (1페이지면 false) */
    public boolean hasPrev() {
        return page > 1;
    }

    //    /** 다음 페이지가 존재하는지? (마지막 페이지면 false) */
    public boolean hasNext() {
        return page < totalPages;
    }

    /** 이전 페이지 번호 반환 (1보다 작아지지 않게) */
    public int prevPage() {
        return (page > 1) ? (page - 1) : 1;
    }

    /** 다음 페이지 번호 반환 (마지막 페이지보다 커지지 않게) */
    public int nextPage() {
        return (page < totalPages) ? (page + 1) : totalPages;
    }
}
