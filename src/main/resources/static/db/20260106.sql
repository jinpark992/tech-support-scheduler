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
CREATE DATABASE IF NOT EXISTS `tech_support_scheduler` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `tech_support_scheduler`;

-- 테이블 tech_support_scheduler.notice 구조 내보내기
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
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 tech_support_scheduler.notice:~46 rows (대략적) 내보내기
INSERT IGNORE INTO `notice` (`notice_id`, `is_top`, `title`, `writer`, `content`, `writer_user_id`, `views`, `created_at`, `updated_at`, `deleted_yn`, `deleted_at`) VALUES
	(20, 1, '상단', 'admin', '상단', NULL, 0, '2025-12-23 12:51:53', '2025-12-23 12:52:24', 'Y', '2025-12-23 12:52:24'),
	(21, 0, '1', 'admin', '1', NULL, 0, '2025-12-23 12:52:02', '2025-12-23 12:52:45', 'Y', '2025-12-23 12:52:45'),
	(22, 0, '2', 'admin', '2', NULL, 0, '2025-12-23 12:52:05', '2025-12-23 12:52:44', 'Y', '2025-12-23 12:52:44'),
	(23, 0, '3', 'admin', '3', NULL, 0, '2025-12-23 12:52:10', '2025-12-23 12:52:44', 'Y', '2025-12-23 12:52:44'),
	(24, 0, '4', 'admin', '4', NULL, 0, '2025-12-23 12:52:15', '2025-12-23 12:52:44', 'Y', '2025-12-23 12:52:44'),
	(25, 1, '1', 'admin', '1', NULL, 0, '2025-12-23 12:52:28', '2025-12-23 12:52:40', 'Y', '2025-12-23 12:52:40'),
	(26, 1, '상단 고정', 'admin', '테스트 중입니다 수정', NULL, 54, '2025-12-23 12:52:54', '2026-01-03 19:59:39', 'N', NULL),
	(27, 0, '일반1 수정', 'admin', '일반1 수정', NULL, 20, '2025-12-23 12:53:01', '2026-01-02 12:44:27', 'Y', '2026-01-02 12:44:27'),
	(28, 0, '일반2 수정', 'admin', '일반2 수정', NULL, 8, '2025-12-23 12:53:06', '2026-01-02 12:44:27', 'Y', '2026-01-02 12:44:27'),
	(29, 0, '일반3', 'admin', '일반3', NULL, 1, '2025-12-23 12:53:15', '2026-01-02 12:44:27', 'Y', '2026-01-02 12:44:27'),
	(30, 0, '일반4', 'admin', '일반4', NULL, 0, '2025-12-23 12:57:27', '2025-12-23 13:14:31', 'Y', '2025-12-23 13:14:31'),
	(31, 1, '상단고정2', 'admin', '상단고정2', NULL, 0, '2025-12-23 12:57:41', '2025-12-23 13:18:45', 'Y', '2025-12-23 13:18:45'),
	(32, 1, '상단3', 'admin', '상단3', NULL, 1, '2025-12-23 13:05:24', '2025-12-23 13:05:28', 'Y', '2025-12-23 13:05:28'),
	(33, 0, '일반4', 'admin', '일반4', NULL, 1, '2025-12-23 13:15:30', '2025-12-31 12:41:02', 'Y', '2025-12-31 12:41:02'),
	(34, 0, '일반5', 'admin', '일반5', NULL, 0, '2025-12-23 13:18:54', '2025-12-23 13:55:59', 'Y', '2025-12-23 13:55:59'),
	(35, 0, '일반5 수정', 'admin', '일반5 수정', NULL, 4, '2025-12-23 14:05:53', '2025-12-24 11:27:57', 'Y', '2025-12-24 11:27:57'),
	(36, 0, '테스트 입니다', 'admin', '테스트 입니다', NULL, 0, '2025-12-24 14:01:11', '2025-12-26 10:46:57', 'Y', '2025-12-26 10:46:57'),
	(37, 0, '11', 'admin', '1', NULL, 0, '2025-12-25 22:50:45', '2025-12-25 22:50:53', 'Y', '2025-12-25 22:50:53'),
	(38, 0, '테스트', 'admin', '테스트', NULL, 2, '2025-12-31 11:20:36', '2026-01-02 12:44:26', 'Y', '2026-01-02 12:44:26'),
	(39, 0, '1', 'admin', '1', NULL, 0, '2025-12-31 12:10:25', '2026-01-02 12:44:26', 'Y', '2026-01-02 12:44:26'),
	(40, 0, '2', 'admin', '2', NULL, 0, '2025-12-31 12:10:28', '2026-01-02 12:44:26', 'Y', '2026-01-02 12:44:26'),
	(41, 0, '3', 'admin', '3', NULL, 0, '2025-12-31 12:10:31', '2026-01-02 12:44:26', 'Y', '2026-01-02 12:44:26'),
	(42, 0, '4', 'admin', '4', NULL, 0, '2025-12-31 12:10:35', '2026-01-02 12:44:26', 'Y', '2026-01-02 12:44:26'),
	(43, 0, '5', 'admin', '5', NULL, 0, '2025-12-31 12:10:38', '2026-01-02 12:44:25', 'Y', '2026-01-02 12:44:25'),
	(44, 0, '6', 'admin', '6', NULL, 0, '2025-12-31 12:10:45', '2026-01-02 12:44:25', 'Y', '2026-01-02 12:44:25'),
	(45, 0, 'ㄷㅂㄷ', 'admin', 'ㅂㅈㄷㅂㅈㄷ', NULL, 0, '2025-12-31 12:37:30', '2026-01-02 12:44:24', 'Y', '2026-01-02 12:44:24'),
	(46, 1, '[필독] 1월 운영 공지', 'admin', '1월 운영/점검 일정 안내', 1, 13, '2026-01-02 12:57:04', '2026-01-05 18:41:55', 'N', NULL),
	(47, 1, '[필독] 장애 대응 프로세스', 'admin', '장애 발생 시 보고/조치 흐름 정리', 1, 39, '2026-01-02 12:57:04', '2026-01-05 18:41:54', 'N', NULL),
	(48, 0, '[공지] 신규 프로젝트 등록 규칙', 'admin', '프로젝트 코드/상태 입력 규칙 안내', 1, 5, '2026-01-02 12:57:04', '2026-01-03 20:41:31', 'Y', '2026-01-03 20:41:31'),
	(49, 0, '[공지] 정기점검 체크리스트', 'admin', '정기점검 항목 공유', 1, 9, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(50, 0, '[공지] 배포 시간대 가이드', 'admin', '배포 권장 시간대 및 사전 공지', 1, 14, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(51, 0, '[공지] DB 백업 정책', 'admin', '백업 주기/보관 기간 안내', 1, 7, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(52, 0, '[공지] 로그 보관 정책', 'admin', '로그 로테이션/보관 기준', 1, 3, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(53, 0, '[공지] 계정/권한 신청 절차', 'admin', '권한 신청 템플릿 공유', 1, 4, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(54, 0, '[공지] 고객사 공지 템플릿', 'admin', '고객 공지 시 사용할 문구', 1, 2, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(55, 0, '[공지] 프로젝트 종료 처리', 'admin', '완료 처리 시 체크 항목', 1, 1, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(56, 0, '[공지] 지원 티켓 작성 규칙', 'admin', '제목/내용/상태 관리 기준', 1, 6, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(57, 0, '[공지] 장애 레벨 정의', 'admin', 'SEV1~SEV3 기준', 1, 8, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(58, 0, '[공지] 회의록 작성 규칙', 'admin', '회의록 템플릿 공유', 1, 0, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(59, 0, '[공지] 서버 점검일 변경', 'admin', '점검일 변경 안내', 1, 11, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(60, 0, '[공지] SLA 기준', 'admin', '응답/처리시간 기준', 1, 10, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(61, 0, '[공지] 신규 기능 배포', 'admin', '신규 기능 안내', 1, 16, '2026-01-02 12:57:04', '2026-01-05 11:36:27', 'N', NULL),
	(62, 0, '[공지] UI 개선 사항', 'admin', '화면 개선 내역 정리', 1, 13, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(63, 0, '[공지] 데이터 정합성 체크', 'admin', '중복/누락 점검 안내', 1, 4, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(64, 0, '[공지] 문의 채널 안내', 'admin', '문의 채널/담당자 안내', 1, 2, '2026-01-02 12:57:04', NULL, 'N', NULL),
	(65, 0, '[공지] 휴일 운영', 'admin', '휴일 대응 방식 안내', 1, 17, '2026-01-02 12:57:04', NULL, 'N', NULL);

-- 테이블 tech_support_scheduler.notice_file 구조 내보내기
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
  `deleted_yn` char(1) NOT NULL DEFAULT 'N',
  `deleted_at` datetime DEFAULT NULL COMMENT '삭제일시',
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `uk_project_code` (`project_code`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 tech_support_scheduler.project:~19 rows (대략적) 내보내기
INSERT IGNORE INTO `project` (`project_id`, `project_code`, `project_name`, `client_name`, `sales_manager`, `contract_amount`, `status`, `contract_date`, `end_date`, `reg_date`, `memo`, `created_at`, `updated_at`, `deleted_yn`, `deleted_at`) VALUES
	(21, 'TS-202601-000001', '흥국생명 WAS 점검', '흥국생명', '홍길동', 15000000, '진행중', '2025-12-10', '2026-01-31', '2025-12-10', '정기점검 + 장애대응 포함', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(22, 'TS-202601-000002', '삼성화재 인증서 연동', '삼성화재', '김영업', 12000000, '대기', '2026-01-05', '2026-02-10', '2026-01-05', '인증서 모듈 연동 + 테스트', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(23, 'TS-202601-000003', '메리츠 운영 이관', '메리츠화재', '박세일즈', 18000000, '진행중', '2025-12-20', '2026-02-28', '2025-12-20', '운영 서버/계정/배포 정리', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(24, 'TS-202601-000004', 'KB 설치지원', 'KB손해보험', '오영업', 7000000, '대기', '2026-01-08', '2026-01-20', '2026-01-08', '설치 일정 협의 중', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(25, 'TS-202601-000005', '신한 장애 대응(긴급)', '신한카드', '최영업', 5000000, '진행중', '2026-01-02', '2026-01-04', '2026-01-02', '로그인 오류 긴급 대응', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(26, 'TS-202601-000006', '현대해상 연간 유지보수', '현대해상', '홍길동', 48000000, '대기', '2026-02-01', '2027-01-31', '2026-01-15', '월 1회 정기점검', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(27, 'TS-202601-000007', '우리은행 서버 증설', '우리은행', '김영업', 9000000, '완료', '2025-11-15', '2025-12-05', '2025-11-15', 'WAS 1대 증설', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(28, 'TS-202601-000008', '롯데 운영 안정화', '롯데카드', '박세일즈', 22000000, '보류', '2026-01-10', '2026-03-31', '2026-01-10', '고객 내부 사정으로 보류', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(29, 'TS-202601-000009', 'NH 인증서 경로 수정', 'NH농협', '오영업', 2000000, '완료', '2025-12-01', '2025-12-03', '2025-12-01', '설정 파일 경로 수정', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(30, 'TS-202601-000010', '한화 단기 장애 분석', '한화생명', '최영업', 3000000, '완료', '2025-12-18', '2025-12-19', '2025-12-18', '원인 분석 및 패치', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(31, 'TS-202601-000011', 'KT 모니터링 추가', 'KT', '홍길동', 4000000, '진행중', '2026-01-03', '2026-01-25', '2026-01-03', '알람/대시보드 연동', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(32, 'TS-202601-000012', 'LGU+ 설치지원', 'LGU+', '김영업', 6500000, '대기', '2026-01-12', '2026-01-18', '2026-01-12', '신규 서버 설치', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(33, 'TS-202601-000013', 'CJ 운영 점검', 'CJ대한통운', '박세일즈', 8000000, '진행중', '2026-01-06', '2026-02-05', '2026-01-06', '정기 점검 + 리포트', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(34, 'TS-202601-000014', '쿠팡 인증서 갱신 지원', '쿠팡', '오영업', 3500000, '진행중', '2026-01-02', '2026-01-10', '2026-01-02', '인증서 갱신/배포', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(35, 'TS-202601-000015', '위메프 장애 재현', '위메프', '최영업', 2500000, '대기', '2026-01-09', '2026-01-12', '2026-01-09', '재현 후 조치 예정', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(36, 'TS-202601-000016', '네이버 유지보수(분기)', '네이버', '홍길동', 10000000, '대기', '2026-01-15', '2026-03-31', '2026-01-15', '분기 점검', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(37, 'TS-202601-000017', '카카오 운영 이슈 조치', '카카오', '김영업', 6000000, '진행중', '2026-01-04', '2026-01-22', '2026-01-04', 'DB 커넥션 이슈', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(38, 'TS-202601-000018', '토스 설치지원', '토스', '박세일즈', 5500000, '대기', '2026-01-20', '2026-01-28', '2026-01-20', '초기 구축 지원', '2026-01-02 12:56:30', NULL, 'N', NULL),
	(39, 'TS-202601-000019', '배민 장애 대응', '배달의민족', '오영업', 4500000, '진행중', '2026-01-02', '2026-01-03', '2026-01-02', '장애 발생 즉시 대응', '2026-01-02 12:56:30', NULL, 'N', NULL);

-- 테이블 tech_support_scheduler.support 구조 내보내기
CREATE TABLE IF NOT EXISTS `support` (
  `ticket_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `support_date` date NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `support_type` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT '접수',
  `title` varchar(200) NOT NULL,
  `content` text DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_yn` char(1) NOT NULL DEFAULT 'N',
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `idx_ticket_date` (`support_date`),
  KEY `idx_ticket_status` (`status`),
  KEY `idx_ticket_deleted` (`deleted_yn`),
  KEY `idx_support_project_id` (`project_id`),
  CONSTRAINT `fk_support_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 tech_support_scheduler.support:~39 rows (대략적) 내보내기
INSERT IGNORE INTO `support` (`ticket_id`, `support_date`, `project_id`, `project_name`, `support_type`, `status`, `title`, `content`, `created_at`, `updated_at`, `deleted_yn`, `deleted_at`) VALUES
	(1, '2025-12-19', NULL, 'OO사 유지보수', '장애지원', '접수', '서버 접속 불가', '오전 10시부터 접속 불가. VPN 확인 및 방화벽 룰 점검 필요.', '2025-12-29 17:33:27', '2026-01-05 18:25:19', 'N', NULL),
	(2, '2025-12-19', NULL, 'OO사 유지보수', '유지보수', '진행', '로그 적재 지연', '배치 로그가 새벽부터 누락. 스케줄러 상태/디스크 용량 확인.', '2025-12-29 17:33:27', '2026-01-05 18:25:19', 'N', NULL),
	(3, '2025-12-20', NULL, 'XX사 설치지원', '설치지원', '진행', '초기 설치 진행', '현장 방문 설치 예정. WAS/Tomcat 버전 체크 및 포트 오픈 요청.', '2025-12-29 17:33:27', '2026-01-05 18:25:19', 'N', NULL),
	(4, '2025-12-31', NULL, 'OO사 유지보수', '유지보수', 'OPEN', '인증서 갱신 안내 수정', '인증서 만료 30일 전. 갱신 절차 및 적용 경로 전달 완료.', '2025-12-29 17:33:27', '2025-12-30 12:48:29', 'N', NULL),
	(5, '2025-12-24', NULL, 'YY사 장애대응', '장애지원', '접수', 'DB 커넥션 에러', 'HikariCP connection timeout 발생. DB 세션/네트워크 상태 확인.', '2025-12-29 17:33:27', '2026-01-05 18:25:19', 'N', NULL),
	(6, '2025-12-24', NULL, 'YY사 장애대응', '장애지원', '진행', 'CPU 100% 급등', '특정 프로세스 점유율 상승. 로그 분석 및 재기동 여부 검토.', '2025-12-29 17:33:27', '2026-01-05 18:25:19', 'N', NULL),
	(7, '2025-12-30', NULL, 'XX사 설치지원', '설치지원', 'OPEN', '운영 반영(수정)', '운영 서버 반영 완료. 서비스 정상 동작 확인 및 인수인계.', '2025-12-29 17:33:27', '2026-01-05 18:25:19', 'N', NULL),
	(8, '2025-12-29', NULL, 'OO사 유지보수', '장애지원', '접수', 'API 응답 지연', '오후 2시부터 응답 지연. APM 확인 후 DB 슬로우쿼리 점검.', '2025-12-29 17:33:27', '2026-01-05 18:25:19', 'N', NULL),
	(9, '2025-12-29', NULL, 'OO사 유지보수', '유지보수', '접수', '백업 실패 알림', '백업 스크립트 실패. 권한/경로 확인 및 재실행 필요.', '2025-12-29 17:33:27', '2026-01-05 18:25:19', 'N', NULL),
	(10, '2025-12-31', NULL, 'ZZ사 정기점검', '유지보수', '예정', '연말 정기 점검', '연말 점검: 로그 정리/디스크 점검/인증서 만료 체크.', '2025-12-29 17:33:27', '2026-01-05 18:25:19', 'N', NULL),
	(11, '2025-12-30', NULL, NULL, '장애지원', 'OPEN', '테스트 티켓', 'DB에서 직접 넣은 테스트 데이터', '2025-12-30 11:19:40', '2026-01-05 18:25:19', 'N', NULL),
	(12, '2025-12-29', NULL, NULL, '유지보수', 'OPEN', '테스트', '테스트', '2025-12-30 11:20:16', '2026-01-05 18:25:19', 'Y', '2025-12-30 12:34:10'),
	(13, '2025-12-30', NULL, NULL, '유지보수', 'OPEN', '테스트2', '테스트2', '2025-12-30 11:20:46', '2026-01-05 18:25:19', 'N', NULL),
	(14, '2025-12-30', NULL, NULL, '유지보수', 'OPEN', '테스트3', '테스트3', '2025-12-30 11:29:24', '2026-01-05 18:25:19', 'N', NULL),
	(15, '2025-12-24', NULL, NULL, '설치지원', 'OPEN', 'ㅋㅋ', 'ㅋㅋㅋ', '2025-12-30 11:51:59', '2026-01-05 18:25:19', 'N', NULL),
	(16, '2025-12-01', NULL, NULL, '유지보수', 'OPEN', '김영업님의 프로젝트', '당장 경주가서 설치해줘!! ', '2025-12-30 12:11:22', '2026-01-05 18:25:19', 'Y', '2025-12-30 12:34:06'),
	(17, '2026-01-02', NULL, NULL, '유지보수', 'OPEN', '신년', '신년', '2026-01-02 12:32:02', '2026-01-05 18:25:19', 'Y', '2026-01-02 12:44:46'),
	(18, '2026-01-02', 25, NULL, '장애지원', 'OPEN', '로그인 오류 발생', '현상 파악 및 로그 수집. 재현 조건 확인', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(19, '2026-01-02', 39, NULL, '장애지원', 'IN_PROGRESS', 'API 지연', 'WAS 스레드/DB 슬로우쿼리 확인', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(20, '2026-01-03', 31, NULL, '유지보수', 'OPEN', '모니터링 알람 추가', 'CPU/메모리/디스크 알람 임계치 정의', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(21, '2026-01-03', 34, NULL, '유지보수', 'IN_PROGRESS', '인증서 갱신 체크', '만료일 확인 및 교체 절차 안내', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(22, '2026-01-04', 37, NULL, '장애지원', 'IN_PROGRESS', 'DB 커넥션 폭증', '풀 설정/타임아웃 점검, 재시도 로직 확인', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(23, '2026-01-05', 22, NULL, '설치지원', 'OPEN', '인증서 모듈 설치', 'WAS 환경 변수/라이브러리 배치', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(24, '2026-01-06', 33, NULL, '유지보수', 'OPEN', '정기 점검', '로그 로테이션/디스크 사용량 확인', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(25, '2026-01-07', NULL, NULL, '유지보수', 'ON_HOLD', '안정화 작업 보류', '고객 요청으로 일정 재조정', '2026-01-02 12:56:55', '2026-01-05 18:25:19', 'N', NULL),
	(26, '2026-01-08', 24, NULL, '설치지원', 'OPEN', '신규 설치 일정 확정', '방문 일정/작업 범위 확정', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(27, '2026-01-09', 35, NULL, '장애지원', 'OPEN', '장애 재현 요청', '테스트 계정/데이터 요청', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(28, '2026-01-10', 28, NULL, '유지보수', 'ON_HOLD', '운영 안정화 보류', '내부 승인 대기', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(29, '2026-01-12', 32, NULL, '설치지원', 'OPEN', '서버 설치', 'Tomcat/DB 연결 설정', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(30, '2026-01-15', 36, NULL, '유지보수', 'OPEN', '분기 점검 준비', '점검 항목/일정 공유', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(31, '2026-01-16', 21, NULL, '유지보수', 'IN_PROGRESS', 'WAS 점검', '서버 상태 확인 및 리포트 작성', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(32, '2026-01-18', 23, NULL, '유지보수', 'IN_PROGRESS', '운영 이관 체크리스트', '계정/권한/배포 이관 확인', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(33, '2026-01-20', 38, NULL, '설치지원', 'OPEN', '초기 구축', '방화벽/포트/계정 요청사항 정리', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(34, '2026-01-21', 31, NULL, '유지보수', 'DONE', '알람 정책 적용 완료', '테스트 알람 수신 확인', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(35, '2026-01-22', 37, NULL, '장애지원', 'DONE', '커넥션 이슈 조치', '풀 사이즈 조정 및 슬로우쿼리 개선', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(36, '2026-01-25', 33, NULL, '유지보수', 'DONE', '점검 리포트 전달', '점검 결과 요약 및 개선안 공유', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(37, '2026-01-31', 21, NULL, '유지보수', 'OPEN', '마감 전 최종 점검', '마감일 기준 최종 확인', '2026-01-02 12:56:55', '2026-01-02 12:56:55', 'N', NULL),
	(38, '2026-01-02', 38, NULL, '유지보수', 'OPEN', '토스 기술 지원', '토스 기술지원 진행중', '2026-01-02 14:04:46', '2026-01-02 14:04:46', 'N', NULL),
	(39, '2026-01-02', NULL, NULL, '유지보수', 'OPEN', '1234', '1234', '2026-01-02 14:17:20', '2026-01-05 18:25:19', 'Y', '2026-01-02 14:17:26');

-- 테이블 tech_support_scheduler.users 구조 내보내기
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 tech_support_scheduler.users:~7 rows (대략적) 내보내기
INSERT IGNORE INTO `users` (`user_id`, `login_id`, `password_hash`, `name`, `role`, `created_at`, `updated_at`, `deleted_yn`) VALUES
	(1, 'admin', '$2a$10$SjfMCO2hJLJVJvxM2xZl8u9X.jl9Lhuldo0RpjELoi3R307g1E7DO', '관리자', 'ROLE_ADMIN', '2025-12-23 11:41:25', '2026-01-05 11:18:40', 'N'),
	(6, 'sales', '$2a$10$H0OvM3UnvkYAabydXYIslur9uYC9iWHl9JqWalowKIt2yGFgpHTWi', '영업', 'ROLE_SALES', '2026-01-02 13:34:05', NULL, 'N'),
	(7, 'support', '$2a$10$H0OvM3UnvkYAabydXYIslur9uYC9iWHl9JqWalowKIt2yGFgpHTWi', '기술지원', 'ROLE_SUPPORT', '2026-01-02 13:34:05', NULL, 'N'),
	(11, 'test', '$2a$10$/SX4ek7vx32U0jNl6jUSH.HCZkQTH4kjOUNW2LuSzfETfuKPU67.K', '박진수', 'ROLE_ADMIN', '2026-01-03 20:25:57', '2026-01-05 12:13:44', 'N'),
	(12, 'dacsar', '$2a$10$BJkBHju0rn0ZIN80iwi1qOgxIK5B8ehWUszph1FkvdKbgYdVY5Zmy', '박진수', 'ROLE_SUPPORT', '2026-01-05 10:03:15', NULL, 'N'),
	(13, 'wlstn127', '$2a$10$8qLF5BEsqxHA/VCOc5GKoubPUCZvBMLssbralaTlLOnxh.KPqhFN6', '홍길동', 'ROLE_ADMIN', '2026-01-05 10:27:07', NULL, 'N'),
	(14, 'test2', '$2a$10$Y3xcOPWTxTjBO2xMZUc2o.UXKoGJ8BXf49adU/z9qTnGuLgIc9UWi', 'test', 'ROLE_ADMIN', '2026-01-05 10:32:24', NULL, 'N');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
