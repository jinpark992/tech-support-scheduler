package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Project {
    private Long projectId;          // project_id (PK)
    private String projectCode;      // project_code
    private String projectName;      // project_name
    private String clientName;       // client_name
    private String salesManager;     // sales_manager

    private Long contractAmount;     // contract_amount
    private String status;           // status

    private LocalDate contractDate;  // contract_date
    private LocalDate endDate;       // end_date
    private LocalDate regDate;       // reg_date

    private String memo;             // memo

    private LocalDateTime createdAt; // created_at
    private LocalDateTime updatedAt; // updated_at

    private String deleteYn;         // delete_yn
    private LocalDateTime deletedAt; // deleted_at

    // 화면용(테이블에 없고 SELECT에서 계산해서 내려줄 값)
    private Integer dday;            // D-12 같은거
}
