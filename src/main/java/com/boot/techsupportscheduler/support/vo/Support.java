package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Support {
    private Long ticketId;            // ticket_id (PK)

    private LocalDate supportDate;    // support_date
    private Long projectId;           // project_id (FK 예정, 지금은 NULL 가능)
    private String projectName;       // project_name (임시 컬럼이면 유지)

    private String supportType;       // support_type (유지보수/설치지원/장애지원)
    private String status;            // status (접수/진행/완료/보류...)

    private String title;             // title
    private String content;           // content

    private LocalDateTime createdAt;  // created_at
    private LocalDateTime updatedAt;  // updated_at

    private String deletedYn;         // deleted_yn ('N'/'Y')
    private LocalDateTime deletedAt;  // deleted_at

    private String salesManager;   // 영업담당자명

}
