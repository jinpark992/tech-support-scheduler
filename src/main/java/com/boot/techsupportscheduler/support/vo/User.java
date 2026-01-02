package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

@Data
public class User {
    private Long userId;
    private String loginId;
    private String passwordHash;
    private String name;      // ✅   추가
    private String role;
}
