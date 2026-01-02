package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

@Data
public class SessionUser {
    private final Long userId;
    private final String loginId;
    private final String role; // ROLE_ADMIN, ROLE_SALES, ROLE_SUPPORT
}
