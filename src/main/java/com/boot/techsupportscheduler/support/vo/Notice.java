package com.boot.techsupportscheduler.support.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Notice {

    private Long noticeId;          // 공지 PK (DB: notice_id)  ex) 1,2,3...
    private String isTop;           // 상단 고정 여부 (DB: is_top)  "Y" / "N"
    private String title;           // 공지 제목 (DB: title)
    private String content;         // 공지 내용 (DB: content)
    private Integer views;          // 조회수 (DB: views)
    private LocalDateTime createdAt; // 작성일시 (DB: created_at)  ex) 2025-12-22 12:00:00
    private LocalDateTime updatedAt; // 수정일시 (DB: updated_at)
    private String deleteYn;        // 삭제여부(소프트 삭제) (DB: delete_yn)  "Y" / "N"
    // ※ 실제 delete 안 하고 Y로만 바꾸는 방식
    private LocalDateTime deletedAt; // 삭제일시 (DB: deleted_at)  deleteYn="Y"일 때만 값 있음
    private String writer; // 작성자(로그인 아이디)

}
