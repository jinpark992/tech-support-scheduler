-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        12.1.2-MariaDB - MariaDB Server
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- tech_support_scheduler 데이터베이스 구조 내보내기
DROP DATABASE IF EXISTS `tech_support_scheduler`;
CREATE DATABASE IF NOT EXISTS `tech_support_scheduler` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `tech_support_scheduler`;

-- 테이블 tech_support_scheduler.notice 구조 내보내기
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
    CONSTRAINT `fk_notice_writer` FOREIGN KEY (`writer_user_id`) REFERENCES `users` (`user_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 tech_support_scheduler.notice:~18 rows (대략적) 내보내기
INSERT IGNORE INTO `notice` (`notice_id`, `is_top`, `title`, `writer`, `content`, `writer_user_id`, `views`, `created_at`, `updated_at`, `deleted_yn`, `deleted_at`) VALUES
	(20, 1, '상단', 'admin', '상단', NULL, 0, '2025-12-23 12:51:53', '2025-12-23 12:52:24', 'Y', '2025-12-23 12:52:24'),
	(21, 0, '1', 'admin', '1', NULL, 0, '2025-12-23 12:52:02', '2025-12-23 12:52:45', 'Y', '2025-12-23 12:52:45'),
	(22, 0, '2', 'admin', '2', NULL, 0, '2025-12-23 12:52:05', '2025-12-23 12:52:44', 'Y', '2025-12-23 12:52:44'),
	(23, 0, '3', 'admin', '3', NULL, 0, '2025-12-23 12:52:10', '2025-12-23 12:52:44', 'Y', '2025-12-23 12:52:44'),
	(24, 0, '4', 'admin', '4', NULL, 0, '2025-12-23 12:52:15', '2025-12-23 12:52:44', 'Y', '2025-12-23 12:52:44'),
	(25, 1, '1', 'admin', '1', NULL, 0, '2025-12-23 12:52:28', '2025-12-23 12:52:40', 'Y', '2025-12-23 12:52:40'),
	(26, 1, '상단 고정', 'admin', '테스트 중입니다 수정', NULL, 23, '2025-12-23 12:52:54', '2025-12-24 11:29:33', 'N', NULL),
	(27, 0, '일반1 수정', 'admin', '일반1 수정', NULL, 13, '2025-12-23 12:53:01', '2025-12-24 11:27:46', 'N', NULL),
	(28, 0, '일반2 수정', 'admin', '일반2 수정', NULL, 8, '2025-12-23 12:53:06', '2025-12-24 11:27:52', 'N', NULL),
	(29, 0, '일반3', 'admin', '일반3', NULL, 1, '2025-12-23 12:53:15', '2025-12-24 11:16:31', 'N', NULL),
	(30, 0, '일반4', 'admin', '일반4', NULL, 0, '2025-12-23 12:57:27', '2025-12-23 13:14:31', 'Y', '2025-12-23 13:14:31'),
	(31, 1, '상단고정2', 'admin', '상단고정2', NULL, 0, '2025-12-23 12:57:41', '2025-12-23 13:18:45', 'Y', '2025-12-23 13:18:45'),
	(32, 1, '상단3', 'admin', '상단3', NULL, 1, '2025-12-23 13:05:24', '2025-12-23 13:05:28', 'Y', '2025-12-23 13:05:28'),
	(33, 0, '일반4', 'admin', '일반4', NULL, 1, '2025-12-23 13:15:30', '2025-12-24 11:16:32', 'N', NULL),
	(34, 0, '일반5', 'admin', '일반5', NULL, 0, '2025-12-23 13:18:54', '2025-12-23 13:55:59', 'Y', '2025-12-23 13:55:59'),
	(35, 0, '일반5 수정', 'admin', '일반5 수정', NULL, 4, '2025-12-23 14:05:53', '2025-12-24 11:27:57', 'Y', '2025-12-24 11:27:57'),
	(36, 0, '테스트 입니다', 'admin', '테스트 입니다', NULL, 0, '2025-12-24 14:01:11', '2025-12-26 10:46:57', 'Y', '2025-12-26 10:46:57'),
	(37, 0, '11', 'admin', '1', NULL, 0, '2025-12-25 22:50:45', '2025-12-25 22:50:53', 'Y', '2025-12-25 22:50:53');

-- 테이블 tech_support_scheduler.notice_file 구조 내보내기
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
    CONSTRAINT `fk_notice_file_notice` FOREIGN KEY (`notice_id`) REFERENCES `notice` (`notice_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 tech_support_scheduler.notice_file:~0 rows (대략적) 내보내기

-- 테이블 tech_support_scheduler.project 구조 내보내기
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
    ) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 tech_support_scheduler.project:~10 rows (대략적) 내보내기
INSERT IGNORE INTO `project` (`project_id`, `project_code`, `project_name`, `client_name`, `sales_manager`, `contract_amount`, `status`, `contract_date`, `end_date`, `reg_date`, `memo`, `created_at`, `updated_at`, `delete_yn`, `deleted_at`) VALUES
	(3, 'TS-202512-8F3K2Q', 'OO사 유지보수', 'OO주식회사', '홍길동', 15000000, '진행중', '2024-05-02', '2025-01-31', '2024-05-02', '정기 점검 + 긴급 장애 대응 포함', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(4, 'TS-202512-9F3K2Q', 'XX사 설치지원', 'XX주식회사', '김영업', 7000000, '대기', '2025-01-05', '2025-01-20', '2024-04-30', '설치 일정 협의 중', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(5, 'TS-202501-A1B2C3', 'YY사 인증서 모듈 연동', 'YY테크', '박세일즈', 12000000, '진행중', '2024-12-10', '2025-02-28', '2024-12-10', 'WAS(Tomcat) + 인증서 연동', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(6, 'TS-202501-Z9X8C7', 'ZZ그룹 통합 점검', 'ZZ그룹', '오영업', 30000000, '보류', '2024-11-01', '2025-03-31', '2024-11-01', '고객 내부 사정으로 일정 보류', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(7, 'TS-202410-K3L9P2', 'AA사 장애 대응(단기)', 'AA솔루션', '홍길동', 2000000, '완료', '2024-10-02', '2024-10-10', '2024-10-02', '장애 원인 분석 및 패치 적용', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(8, 'TS-202412-Q1W2E3', 'BB사 운영 이관', 'BB주식회사', '최영업', 18000000, '진행중', '2024-12-20', '2025-04-30', '2024-12-20', '운영 서버 이관/계정/배포 파이프라인 정리', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(9, 'TS-202503-R7T6Y5', 'CC사 정기 유지보수(연간)', 'CC엔터프라이즈', '김영업', 48000000, '대기', '2025-03-01', '2026-02-28', '2025-02-20', '연간 계약, 월 1회 정기점검', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(10, 'TS-202411-M5N4B3', 'DD사 서버 증설', 'DD시스템', '박세일즈', 9000000, '완료', '2024-11-15', '2024-12-05', '2024-11-15', 'WAS 1대 증설 및 모니터링 추가', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(11, 'TS-202502-U8I7O6', 'EE사 기술지원(긴급)', 'EE커머스', NULL, 5000000, '진행중', '2025-02-01', '2025-02-10', '2025-02-01', '영업담당 미지정 케이스(테스트용)', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(12, 'TS-202506-P0O9I8', 'FF사 재계약 협상', 'FF물류', '오영업', 0, '대기', NULL, NULL, '2025-01-10', '계약금액/기간 미확정(입력 검증용)', '2025-12-26 11:07:41', NULL, 'N', NULL),
	(15, 'TS-202512-3D4D67B8', '흥국생명', '흥국생명', NULL, 1111111, '대기', '2025-12-26', '2025-12-25', '2025-12-26', '111111', '2025-12-26 12:04:36', '2025-12-26 12:04:36', 'N', NULL),
	(16, 'TS-202512-028EE5FB', '흥국생명', '흥국생명', NULL, 1111111, '대기', '2025-12-26', '2025-12-25', '2025-12-26', '111111', '2025-12-26 12:05:16', '2025-12-26 12:05:16', 'N', NULL),
	(17, 'TS-202512-D16A17CA', '흥국생명', '흥국생명', NULL, 1111111, '대기', '2025-12-26', '2025-12-25', '2025-12-26', '111111', '2025-12-26 12:07:50', '2025-12-26 12:07:50', 'N', NULL),
	(18, 'TS-202512-4D945AEA', '삼성화재', '삼성화재', '박진수', 12345, '진행중', '2025-11-30', '2025-12-31', '2025-12-26', 'ㅋㅋㅋㅋ', '2025-12-26 12:13:23', '2025-12-26 12:13:23', 'N', NULL),
	(19, 'TS-202512-77A72E2B', '메리츠화재', '메리츠', '홍길동', 111111, '진행중', '2025-12-29', '2025-12-31', '2025-12-26', 'ㅇㅇㅋㅇㅋㅇ', '2025-12-26 12:30:18', '2025-12-26 12:30:18', 'N', NULL);

-- 테이블 tech_support_scheduler.users 구조 내보내기
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

-- 테이블 데이터 tech_support_scheduler.users:~1 rows (대략적) 내보내기
INSERT IGNORE INTO `users` (`user_id`, `login_id`, `password_hash`, `name`, `role`, `created_at`, `updated_at`, `deleted_yn`) VALUES
	(1, 'admin', 'admin', '관리자', 'ADMIN', '2025-12-23 11:41:25', NULL, 'N');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
