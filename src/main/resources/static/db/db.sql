DROP DATABASE IF EXISTS `tech_support_scheduler`;
CREATE DATABASE IF NOT EXISTS `tech_support_scheduler` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tech_support_scheduler`;

-- 1) users 먼저
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
                                       `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `login_id` varchar(50) NOT NULL,
    `password_hash` varchar(255) NOT NULL,
    `name` varchar(50) NOT NULL,
    `role` varchar(20) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    `deleted_yn` char(1) NOT NULL DEFAULT 'N',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `login_id` (`login_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO `users` (`user_id`, `login_id`, `password_hash`, `name`, `role`, `created_at`, `updated_at`, `deleted_yn`) VALUES
(1, 'admin', 'admin', '관리자', 'ADMIN', '2025-12-23 11:41:25', NULL, 'N');

-- 2) notice (users가 있으니 FK 생성 가능)
DROP TABLE IF EXISTS `notice`;
CREATE TABLE IF NOT EXISTS `notice` (
                                        `notice_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `is_top` tinyint(1) NOT NULL DEFAULT 0,
    `title` varchar(200) NOT NULL,
    `writer` varchar(50) NOT NULL DEFAULT 'admin',
    `content` text NOT NULL,
    `writer_user_id` bigint(20) DEFAULT NULL,
    `views` int(11) NOT NULL DEFAULT 0,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    `deleted_yn` char(1) NOT NULL DEFAULT 'N',
    `deleted_at` datetime DEFAULT NULL,
    PRIMARY KEY (`notice_id`),
    KEY `fk_notice_writer` (`writer_user_id`),
    CONSTRAINT `fk_notice_writer`
    FOREIGN KEY (`writer_user_id`) REFERENCES `users` (`user_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- notice INSERT는 네 기존 INSERT 그대로 붙이면 됨

-- 3) notice_file
DROP TABLE IF EXISTS `notice_file`;
CREATE TABLE IF NOT EXISTS `notice_file` (
                                             `file_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `notice_id` bigint(20) NOT NULL,
    `origin_name` varchar(255) NOT NULL,
    `save_name` varchar(255) NOT NULL,
    `content_type` varchar(100) DEFAULT NULL,
    `file_size` bigint(20) DEFAULT NULL,
    `save_path` varchar(500) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `deleted_yn` char(1) NOT NULL DEFAULT 'N',
    PRIMARY KEY (`file_id`),
    KEY `idx_notice_file_notice_id` (`notice_id`),
    CONSTRAINT `fk_notice_file_notice`
    FOREIGN KEY (`notice_id`) REFERENCES `notice` (`notice_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 4) project
DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project` (
                                         `project_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `project_code` varchar(20) NOT NULL COMMENT '프로젝트 코드(랜덤/규칙 기반) 예: TS-8F3K2Q',
    `project_name` varchar(100) NOT NULL COMMENT '프로젝트명',
    `client_name` varchar(100) NOT NULL COMMENT '고객사',
    `sales_manager` varchar(50) DEFAULT NULL COMMENT '영업 담당자(표시용 텍스트, 추후 user_id로 교체 가능)',
    `contract_amount` bigint(20) NOT NULL DEFAULT 0 COMMENT '수주금액(숫자만 저장)',
    `status` varchar(20) NOT NULL DEFAULT '대기' COMMENT '상태(대기/진행중/완료/보류 등)',
    `contract_date` date DEFAULT NULL COMMENT '계약일(시작일)',
    `end_date` date DEFAULT NULL COMMENT '종료예정일',
    `reg_date` date NOT NULL COMMENT '등록일(업무 기준일)',
    `memo` varchar(500) DEFAULT NULL COMMENT '비고',
    `created_at` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp() COMMENT '수정일시',
    `delete_yn` char(1) NOT NULL DEFAULT 'N' COMMENT '삭제여부(소프트삭제)',
    `deleted_at` datetime DEFAULT NULL COMMENT '삭제일시',
    PRIMARY KEY (`project_id`),
    UNIQUE KEY `uk_project_code` (`project_code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- project INSERT도 네 기존 INSERT 그대로 붙이면 됨
