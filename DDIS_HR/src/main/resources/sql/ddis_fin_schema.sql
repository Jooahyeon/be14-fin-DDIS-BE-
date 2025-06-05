SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `notice`;
DROP TABLE IF EXISTS `selfreviewfile`;
DROP TABLE IF EXISTS `contract`;
DROP TABLE IF EXISTS `appointment`;
DROP TABLE IF EXISTS `review`;
DROP TABLE IF EXISTS `review_grade`;
DROP TABLE IF EXISTS `approval_line`;
DROP TABLE IF EXISTS `leave`;
DROP TABLE IF EXISTS `retirement_pay`;
DROP TABLE IF EXISTS `participant`;
DROP TABLE IF EXISTS `personal_schedule`;
DROP TABLE IF EXISTS `disciplinary`;
DROP TABLE IF EXISTS `document_box`;
DROP TABLE IF EXISTS `draft_documents`;
DROP TABLE IF EXISTS `document_attachment`; -- ✅ 추가
DROP TABLE IF EXISTS `meeting`;
DROP TABLE IF EXISTS `selfreview`;
DROP TABLE IF EXISTS `attendance`;
DROP TABLE IF EXISTS `appointment_history`;
DROP TABLE IF EXISTS `goal`;
DROP TABLE IF EXISTS `leave_history`;
DROP TABLE IF EXISTS `leave_alert_log`; -- ✅ 추가
DROP TABLE IF EXISTS `salary`;
DROP TABLE IF EXISTS `salary_base`;
DROP TABLE IF EXISTS `board`;
DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `team`;
DROP TABLE IF EXISTS `department`;
DROP TABLE IF EXISTS `introduction`; -- ✅ 추가
DROP TABLE IF EXISTS `position`;
DROP TABLE IF EXISTS `job`;
DROP TABLE IF EXISTS `work_status`;
DROP TABLE IF EXISTS `document_form`;
DROP TABLE IF EXISTS `headquarters`;
DROP TABLE IF EXISTS `rank`;
DROP TABLE IF EXISTS `holiday`;
DROP TABLE IF EXISTS `dictionary`;

-- 주석 처리된 항목들
-- DROP TABLE IF EXISTS `menu`;
-- DROP TABLE IF EXISTS `favorite_menu`;

SET FOREIGN_KEY_CHECKS = 1;


-- (이미 FK_CHECKS 비활성화 후 DROP 구문이 있다고 가정)

-- 1. 의존성 없는 테이블
-- 공휴일
CREATE TABLE `holiday` (
    `date`            DATE NOT NULL,
    `is_holiday`      BOOLEAN      NOT NULL,
    `holiday_name`    VARCHAR(255),
    `is_weekend`      BOOLEAN      NOT NULL,
    PRIMARY KEY (`date`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 용어사전
CREATE TABLE `dictionary` (
    `dictionary_id`	BIGINT	NOT NULL AUTO_INCREMENT,
    `dictionary_name`	VARCHAR(255)	NOT NULL,
    `dictionary_content`	TEXT	NOT NULL,
    `dictionary_type`	VARCHAR(255)	NOT NULL,
    PRIMARY KEY (`dictionary_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 2. 직무 쪽
-- 본부
CREATE TABLE `headquarters` (
    `head_id`   BIGINT NOT NULL AUTO_INCREMENT,
    `head_name` VARCHAR(255) NOT NULL,
    `head_code` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`head_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 부서
CREATE TABLE `department` (
    `department_id` BIGINT NOT NULL AUTO_INCREMENT,
    `department_name` VARCHAR(255) NOT NULL,
    `department_code` VARCHAR(255) NOT NULL,
    `head_id`        BIGINT NOT NULL,
    PRIMARY KEY (`department_id`),
    FOREIGN KEY (`head_id`) REFERENCES `headquarters`(`head_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 팀
CREATE TABLE `team` (
    `team_id`       BIGINT      NOT NULL AUTO_INCREMENT,
    `team_name`     VARCHAR(255) NOT NULL,
    `team_code`     VARCHAR(255) NOT NULL,
    `department_id` BIGINT      NOT NULL,
    PRIMARY KEY (`team_id`),
    FOREIGN KEY (`department_id`) REFERENCES `department`(`department_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 소개
CREATE TABLE introduction (
    introduction_id     BIGINT NOT NULL AUTO_INCREMENT,
    introduction_context VARCHAR(255) NOT NULL,
    created_at           VARCHAR(255) NOT NULL,
    introduction_type    VARCHAR(255) NOT NULL,
    department_id        BIGINT,
    team_id              BIGINT,
    PRIMARY KEY (`introduction_id`),
    FOREIGN KEY (`department_id`) REFERENCES department(`department_id`),
    FOREIGN KEY (`team_id`) REFERENCES team(`team_id`),
    CHECK (`introduction_type` IN ('부서', '팀'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 직책
CREATE TABLE `position` (
    `position_id`   BIGINT NOT NULL AUTO_INCREMENT,
    `position_name` VARCHAR(255) NOT NULL,
    `position_code` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`position_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 직급
CREATE TABLE `rank` (
    `rank_id`   BIGINT NOT NULL AUTO_INCREMENT,
    `rank_name` VARCHAR(255) NOT NULL,
    `rank_order` INT NOT NULL,
    `rank_code` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`rank_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 직무
CREATE TABLE `job` (
    `job_id`   BIGINT NOT NULL AUTO_INCREMENT,
    `job_name` VARCHAR(255) NOT NULL,
    `job_code` VARCHAR(255) NOT NULL,
    `job_role` VARCHAR(255) NOT NULL,
    `job_need` VARCHAR(255) NOT NULL,
    `job_necessary` VARCHAR(255) NOT NULL,
    `job_preference` VARCHAR(255) NOT NULL,
    `team_id` BIGINT NOT NULL,
    PRIMARY KEY (`job_id`),
    FOREIGN KEY (`team_id`) REFERENCES team(`team_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;
-- 3. 독립 테이블
-- 근무 상태
CREATE TABLE `work_status` (
    `work_status_id` VARCHAR(255) NOT NULL,
    `work_status_name` VARCHAR(255) NOT NULL,
    `label_color`    VARCHAR(255) NOT NULL,
    `sort_order`     INT NOT NULL,
    PRIMARY KEY (`work_status_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 4. 연결 테이블
-- 사원
CREATE TABLE `employee` (
    `employee_id`   BIGINT NOT NULL,
    `employee_name` VARCHAR(255) NOT NULL,
    `employee_pwd`  VARCHAR(255) NOT NULL,
    `employee_profile` VARCHAR(255),
    `employee_nation` VARCHAR(255) NOT NULL,
    `employee_gender` VARCHAR(255) NOT NULL,
    `employee_birth` DATE NOT NULL,
    `employee_resident` VARCHAR(255) NOT NULL,
    `employee_contact` VARCHAR(255) NOT NULL,
    `employee_email` VARCHAR(255) NOT NULL,
    `employee_address` VARCHAR(255) NOT NULL,
    `employment_date` DATE NOT NULL,
    `retirement_date` DATE,
    `work_type`     BOOLEAN NOT NULL DEFAULT TRUE,
    `bank_name`     VARCHAR(255) NOT NULL,
    `bank_depositor` VARCHAR(255) NOT NULL,
    `bank_account`  VARCHAR(255) NOT NULL,
    `is_disorder`   BOOLEAN NOT NULL DEFAULT FALSE,
    `military_type`   VARCHAR(255) NOT NULL,
    `is_marriage`   BOOLEAN NOT NULL DEFAULT TRUE,
    `marriage_date` DATE,
    `family_count` INT NOT NULL,
    `career_year_count` INT,
    `previous_company` VARCHAR(255),
    `final_academic` VARCHAR(255) NOT NULL,
    `employee_school` VARCHAR(255) NOT NULL,
    `employee_dept`  VARCHAR(255),
    `graduation_year` INT,
    `is_four_insurances` BOOLEAN NOT NULL DEFAULT TRUE,
    `position_id`   BIGINT NOT NULL,
    `rank_id`       BIGINT NOT NULL,
    `job_id`        BIGINT,
    `head_id`       BIGINT,
    `department_id` BIGINT,
    `team_id`       BIGINT,
    PRIMARY KEY (`employee_id`),
    FOREIGN KEY (`position_id`)   REFERENCES `position`(`position_id`),
    FOREIGN KEY (`rank_id`)       REFERENCES `rank`(`rank_id`),
    FOREIGN KEY (`job_id`)        REFERENCES `job`(`job_id`),
    FOREIGN KEY (`head_id`)       REFERENCES `headquarters`(`head_id`),
    FOREIGN KEY (`department_id`) REFERENCES `department`(`department_id`),
    FOREIGN KEY (`team_id`)       REFERENCES `team`(`team_id`),
    CHECK (`military_type` IN ('군필', '보충역', '면제', '미필'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 전자결재양식
CREATE TABLE `document_form` (
     `form_id`      BIGINT NOT NULL AUTO_INCREMENT,
     `form_name`    VARCHAR(255) NOT NULL,
     `form_content` LONGTEXT   NOT NULL,
     `is_deleted`   BOOLEAN DEFAULT FALSE,
     `employee_id`  BIGINT,                          -- ✅ 컬럼 추가!
     PRIMARY KEY (`form_id`),
     FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


-- 5. 이어지는 자식 테이블들
-- 결재 부분
-- 전자결재 기안문서
CREATE TABLE `draft_documents` (
    `doc_id`      BIGINT NOT NULL AUTO_INCREMENT,
    `doc_title`   VARCHAR(255) NOT NULL,
    `doc_content` LONGTEXT,
    `preserve_period` VARCHAR(255) NOT NULL DEFAULT 5,
    `expiration_date` DATE NOT NULL,
    `doc_status`  VARCHAR(255)  NOT NULL DEFAULT '대기중',
    `created_at`  DATETIME NOT NULL,
    `submitted_at` DATETIME,
    `draft_saved_at` DATETIME,
    `final_approval_at` DATETIME,
    `deleted_at`  DATETIME,
    `draft_version` INT NOT NULL,
    `form_id`     BIGINT NOT NULL,
    `employee_id` BIGINT NOT NULL,
    PRIMARY KEY (`doc_id`),
    FOREIGN KEY (`form_id`)     REFERENCES `document_form`(`form_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`),
    CHECK (`preserve_period` IN (1, 3, 5)),
    CHECK (doc_status IN ('대기중','임시저장','심사중','반려','회수','결재완료'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 전자결재 파일 첨부
CREATE TABLE `document_attachment` (
    `attachment_id` BIGINT NOT NULL AUTO_INCREMENT,
    `file_name`     VARCHAR(255) NOT NULL,
    `file_url`      VARCHAR(255) NOT NULL,
    `file_size`     BIGINT NOT NULL,
    `file_type`     VARCHAR(255) NOT NULL,
    `is_deleted`    BOOLEAN NOT NULL DEFAULT FALSE,
    `doc_id`        BIGINT NOT NULL,
    PRIMARY KEY (`attachment_id`),
    FOREIGN KEY (`doc_id`) REFERENCES `draft_documents`(`doc_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 결재라인
CREATE TABLE `approval_line` (
     `approval_line_id` BIGINT      NOT NULL AUTO_INCREMENT,
     `step`             INT         NOT NULL,
     `status`           VARCHAR(20) NOT NULL DEFAULT '대기중'
         CHECK(status IN ('기안','미결','대기중','승인','반려','회수됨')),
     `approved_at`      DATETIME,
     `type`             VARCHAR(20) NOT NULL DEFAULT '내부'
         CHECK(type IN ('기안','협조','내부')),
     `opinion`          VARCHAR(255),
     `department_id`    BIGINT,
     `position_id`      BIGINT,
     `job_id`           BIGINT,
     `team_id`          BIGINT,
     `rank_id`          BIGINT,
     `employee_id`      BIGINT,
     `line_type`        VARCHAR(255) NOT NULL DEFAULT 'ACTUAL' CHECK(line_type IN('ACTUAL','TEMPLATE')),
     `viewed_at`        DATETIME,
     `doc_id`           BIGINT,
     `form_id`          BIGINT      NOT NULL,
     PRIMARY KEY (`approval_line_id`,`step`),
     FOREIGN KEY (`doc_id`)  REFERENCES `draft_documents`(`doc_id`),
     FOREIGN KEY (`form_id`) REFERENCES `document_form`(`form_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 문서결재함
CREATE TABLE `document_box` (
    `employee_id` BIGINT NOT NULL,
    `doc_id`      BIGINT NOT NULL,
    `is_read`     BOOLEAN NOT NULL DEFAULT FALSE,
    `read_at`     DATETIME,
    `is_deleted`  BOOLEAN NOT NULL DEFAULT FALSE,
    `role`        VARCHAR(255) NOT NULL DEFAULT '미정',
    PRIMARY KEY (`employee_id`,`doc_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE,
    FOREIGN KEY (`doc_id`)      REFERENCES `draft_documents`(`doc_id`) ON DELETE CASCADE,
    CHECK(`role` IN ('미정', '기안자', '결재자', '협조자', '수신자', '참조자'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 급여 부분
-- 급여 기초
CREATE TABLE `salary_base` (
   `rank_id`      BIGINT NOT NULL,
   `annual_income` INT   NOT NULL,
   PRIMARY KEY (`rank_id`),
   FOREIGN KEY (`rank_id`) REFERENCES `rank`(`rank_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 급여
CREATE TABLE `salary` (
    `salary_id`     BIGINT NOT NULL AUTO_INCREMENT,
    `employee_id`   BIGINT NOT NULL,
    `salary_date`   DATETIME NOT NULL,
    `salary_basic`  INT NOT NULL,
    `salary_overtime` INT NOT NULL DEFAULT 0,
    `salary_night`  INT NOT NULL DEFAULT 0,
    `salary_holiday` INT NOT NULL DEFAULT 0,
    `salary_meal`   INT NOT NULL DEFAULT 0,
    `salary_transport` INT NOT NULL DEFAULT 0,
    `salary_bonus`  INT NOT NULL DEFAULT 0,
    `income_tax`    INT NOT NULL DEFAULT 0,
    `local_income_tax` INT NOT NULL DEFAULT 0,
    `national_pension` INT NOT NULL DEFAULT 0,
    `employment_insurance` INT NOT NULL DEFAULT 0,
    `health_insurance` INT NOT NULL DEFAULT 0,
    `long_care_insurance` INT NOT NULL DEFAULT 0,
    `total_income`  INT NOT NULL DEFAULT 0,
    `total_deductions` INT NOT NULL DEFAULT 0,
    `net_salary`    INT NOT NULL DEFAULT 0,
    PRIMARY KEY (`salary_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 퇴직금
CREATE TABLE `retirement_pay` (
    `retirement_id`         BIGINT NOT NULL AUTO_INCREMENT,
    `employee_id`           BIGINT NOT NULL,
    `total_bonus`           INT NOT NULL DEFAULT 0,
    `month1`                VARCHAR(255) NOT NULL,
    `month1_salary`         INT NOT NULL DEFAULT 0,
    `month2`                VARCHAR(255) NOT NULL,
    `month2_salary`         INT NOT NULL DEFAULT 0,
    `month3`                VARCHAR(255) NOT NULL,
    `month3_salary`         INT NOT NULL DEFAULT 0,
    `month4`                VARCHAR(255),
    `month4_salary`         INT DEFAULT 0,
    `retire_total`          INT NOT NULL DEFAULT 0,
    `retire_income_tax`     INT NOT NULL DEFAULT 0,
    `total_days`            INT NOT NULL DEFAULT 0,
    `provision_actual`      INT NOT NULL DEFAULT 0,
    `provision_situation`   VARCHAR(255) NOT NULL DEFAULT '미지급',
    `provision_date`        DATETIME,
    `remark`                VARCHAR(255),
    PRIMARY KEY (`retirement_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`),
    CHECK ( `provision_situation` IN ('미지급', '지급완료', '지연'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 근태 부분
-- 연차
CREATE TABLE `leave` (
     `employee_id`      BIGINT NOT NULL,
     `total_days`       INT    NOT NULL DEFAULT 0,
     `used_days`        DECIMAL(3,1)    NOT NULL DEFAULT 0,
     `remaining_days`   DECIMAL(3,1)    NOT NULL DEFAULT 0,
     `pending_leave_days` INT  NOT NULL DEFAULT 0,
     `first_notice_date`  DATE,
     `second_notice_date` DATE,
     PRIMARY KEY (`employee_id`),
     FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 연차사용이력
CREATE TABLE `leave_history` (
    `history_id`     BIGINT NOT NULL AUTO_INCREMENT,
    `employee_id`    BIGINT NOT NULL,
    `leave_type`     VARCHAR(255) NOT NULL DEFAULT '연차',
    `request_date`   DATE NOT NULL,
    `start_date`     DATE NOT NULL,
    `end_date`       DATE NOT NULL,
    `reason`         VARCHAR(255) NOT NULL,
    `leave_days`     DECIMAL(3,1) NOT NULL,
    `approval_status` VARCHAR(255) NOT NULL DEFAULT '대기중',
    `reject_reason`  VARCHAR(255),
    PRIMARY KEY (`history_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE,
    CHECK(`leave_type` IN ('연차','오전반차','오후반차')),
    CHECK(`approval_status` IN ('승인','대기중','반려'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 촉진이력
CREATE TABLE `leave_alert_log` (
    `leave_alert_log_id` BIGINT NOT NULL AUTO_INCREMENT,
    `employee_id`   BIGINT NOT NULL,
    `alert_type`   VARCHAR(255) NOT NULL DEFAULT 'FIRST',
    `send_datetime` DATETIME NOT NULL,
    PRIMARY KEY (`leave_alert_log_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`),
    CHECK(`alert_type` IN ('FIRST', 'SECOND'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 근무
CREATE TABLE `attendance` (
    `attendance_id` BIGINT NOT NULL AUTO_INCREMENT,
    `employee_id`   BIGINT NOT NULL,
    `work_status_id` VARCHAR(255) NOT NULL,
    `work_date`     DATE NOT NULL,
    `check_in_time`  TIME,
    `check_out_time` TIME,
    `work_duration` INT,
    `overtime_type` VARCHAR(255) DEFAULT '시간외근무',
    `overtime_duration` INT,
    `request_time`   DATETIME,
    `requested_time_change` DATETIME,
    `approval_status` VARCHAR(255) DEFAULT '대기중',
    `processed_time` DATETIME,
    `reason` VARCHAR(255),
    `reject_reason` VARCHAR(255),
    PRIMARY KEY (`attendance_id`),
    FOREIGN KEY (`employee_id`)     REFERENCES `employee`(`employee_id`) ON DELETE CASCADE,
    FOREIGN KEY (`work_status_id`)  REFERENCES `work_status`(`work_status_id`) ON DELETE CASCADE,
    CHECK(overtime_type IN ('시간외근무','야간근무','휴일근무')),
    CHECK(approval_status IN ('승인','대기중','반려'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 회의
CREATE TABLE `meeting` (
   `meeting_id`  BIGINT NOT NULL AUTO_INCREMENT,
   `team_id`     BIGINT NOT NULL,
   `employee_id` BIGINT NOT NULL,
   `meeting_date` DATE NOT NULL,
   `meeting_title` VARCHAR(255) NOT NULL,
   `meeting_time` VARCHAR(255) NOT NULL,
   PRIMARY KEY (`meeting_id`),
   FOREIGN KEY (`team_id`)     REFERENCES `team`(`team_id`),
   FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 회의참여인원
CREATE TABLE `participant` (
    `participant_id` BIGINT NOT NULL AUTO_INCREMENT,
    `meeting_id`     BIGINT NOT NULL,
    `employee_id`    BIGINT NOT NULL,
    PRIMARY KEY (`participant_id`),
    FOREIGN KEY (`meeting_id`)  REFERENCES `meeting`(`meeting_id`) ON DELETE CASCADE,
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 개인일정
CREATE TABLE `personal_schedule` (
     `personal_schedule_id` BIGINT NOT NULL AUTO_INCREMENT,
     `employee_id`          BIGINT NOT NULL,
     `schedule_date`        DATE   NOT NULL,
     `schedule_title`       VARCHAR(255) NOT NULL,
     `schedule_time`        VARCHAR(255) NOT NULL,
     PRIMARY KEY (`personal_schedule_id`),
     FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 계약 및 징계
-- 계약
CREATE TABLE `contract` (
    `contract_id`      BIGINT NOT NULL AUTO_INCREMENT,
    `employee_id`      BIGINT NOT NULL,
    `contract_descrip` VARCHAR(255) NOT NULL,
    `contract_file`    VARCHAR(255) NOT NULL,
    `contract_file_size` BIGINT,
    `request_date`	DATE	NOT NULL,
    `contract_date`	DATE	NOT NULL,
    `end_date`	DATE	NOT NULL,
    PRIMARY KEY (`contract_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 징계
CREATE TABLE `disciplinary` (
    `disciplinary_id`  BIGINT NOT NULL AUTO_INCREMENT,
    `employee_id`      BIGINT NOT NULL,
    `disciplinary_descrip` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`disciplinary_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 평가 및 성과 부분
-- 목표
CREATE TABLE `goal` (
    `goal_id`        BIGINT NOT NULL AUTO_INCREMENT,
    `goal_title`     VARCHAR(255) NOT NULL,
    `goal_content`   TEXT,
    `goal_value`     DOUBLE NOT NULL,
    `goal_weight`    DOUBLE NOT NULL,
    `goal_created_at` DATETIME NOT NULL,
    `employee_id`    BIGINT NOT NULL,
    PRIMARY KEY (`goal_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 자기평가
CREATE TABLE `selfreview` (
    `selfreview_id`   BIGINT NOT NULL AUTO_INCREMENT,
    `performance_value` DOUBLE,
    `selfreview_status` ENUM('대기','승인','반려'),
    `selfreview_created_at` DATETIME,
    `selfreview_approve_date` DATETIME,
    `goal_id`         BIGINT(255) NOT NULL,
    `employee_id_selfreviewer` BIGINT NOT NULL,
    `selfreview_content` TEXT,
    `reviewer_score`   INT,
    `reviewer_created_at` DATETIME,
    `reviewer_content` TEXT,
    `employee_id_reviewer` BIGINT,
    PRIMARY KEY (`selfreview_id`),
    FOREIGN KEY (`goal_id`)                 REFERENCES `goal`(`goal_id`),
    FOREIGN KEY (`employee_id_selfreviewer`) REFERENCES `employee`(`employee_id`),
    FOREIGN KEY (`employee_id_reviewer`)     REFERENCES `employee`(`employee_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 실적파일
CREATE TABLE `selfreviewfile` (
    `selfreview_file_id`	VARCHAR(255) NOT NULL,
    `selfreview_file_name`	VARCHAR(255),
    `selfreview_file_url`	VARCHAR(255)	NOT NULL,
    `selfreview_file_upload_date`	DATETIME	NOT NULL,
    `employee_id`	BIGINT	NOT NULL,
    `selfreview_id`	BIGINT	NOT NULL,
    `selfreview_file_size`	VARCHAR(255)	NOT NULL,
    `selfreview_file_type`	VARCHAR(255)	NOT NULL,
    PRIMARY KEY (`selfreview_file_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE,
    FOREIGN KEY (`selfreview_id`) REFERENCES `selfreview`(selfreview_id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 평가등급
CREATE TABLE `review_grade` (
    `review_grade_id` INT    NOT NULL AUTO_INCREMENT,
    `review_min_score` INT   NOT NULL,
    `review_max_score` INT   NOT NULL,
    `review_grade`     CHAR NOT NULL,
    `employee_id`      BIGINT NOT NULL,
    PRIMARY KEY (`review_grade_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 평가
CREATE TABLE `review` (
    `review_id`        BIGINT NOT NULL auto_increment,
    `review_score`     INT         NOT NULL,
    `review_grade_id`  INT         NOT NULL,
    `employee_id`      BIGINT      NOT NULL,
    `selfreview_id`    BIGINT NOT NULL,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`review_grade_id`) REFERENCES `review_grade`(`review_grade_id`),
    FOREIGN KEY (`employee_id`)     REFERENCES `employee`(`employee_id`),
    FOREIGN KEY (`selfreview_id`)   REFERENCES `selfreview`(`selfreview_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 인사발령 부분
-- 인사발령
CREATE TABLE `appointment` (
    `appointment_id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `employee_id`             BIGINT      NOT NULL,
    `from_head_code`          VARCHAR(255) NOT NULL,
    `to_head_code`            VARCHAR(255),
    `from_department_code`    VARCHAR(255) NOT NULL,
    `to_department_code`      VARCHAR(255),
    `from_team_code`          VARCHAR(255) NOT NULL,
    `to_team_code`            VARCHAR(255),
    `from_position_code`      VARCHAR(255) NOT NULL,
    `to_position_code`        VARCHAR(255),
    `from_rank_code`          VARCHAR(255) NOT NULL,
    `to_rank_code`            VARCHAR(255),
    `from_job_code`           VARCHAR(255) NOT NULL,
    `to_job_code`             VARCHAR(255),
    `appointment_type`        VARCHAR(255) NOT NULL,
    `appointment_reason`      VARCHAR(255) NOT NULL,
    `appointment_created_at`  DATE NOT NULL,
    `appointment_effective_date` DATE NOT NULL,
    `appointment_status`      VARCHAR(255) NOT NULL DEFAULT '대기',
    `is_applied`              BOOLEAN  NOT NULL DEFAULT TRUE,
    PRIMARY KEY (`appointment_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`),
    CHECK (`appointment_type` IN ('전보', '전직', '승진', '직급조정', '입사', '퇴사')),
    CHECK (`appointment_status` IN ('대기', '승인', '반려'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 발령이력
CREATE TABLE `appointment_history` (
    `appointment_history_id` BIGINT NOT NULL AUTO_INCREMENT,
    `appointment_id`         BIGINT NOT NULL,
    `employee_id`            BIGINT NOT NULL,
    `from_head_code`         VARCHAR(255) NOT NULL,
    `to_head_code`           VARCHAR(255),
    `from_department_code`   VARCHAR(255) NOT NULL,
    `to_department_code`     VARCHAR(255),
    `from_team_code`         VARCHAR(255) NOT NULL,
    `to_team_code`           VARCHAR(255),
    `from_position_code`     VARCHAR(255) NOT NULL,
    `to_position_code`       VARCHAR(255),
    `from_rank_code`         VARCHAR(255) NOT NULL,
    `to_rank_code`           VARCHAR(255),
    `from_job_code`          VARCHAR(255) NOT NULL,
    `to_job_code`            VARCHAR(255),
    `appointment_type`       VARCHAR(255) NOT NULL,
    `appointment_reason`     VARCHAR(255) NOT NULL,
    `appointment_created_at`  DATE NOT NULL,
    `appointment_effective_date` DATE NOT NULL,
    `appointment_status`      VARCHAR(255) NOT NULL DEFAULT '대기',
    `appointment_history_created_at` DATE NOT NULL,
    PRIMARY KEY (`appointment_history_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`),
    CHECK (`appointment_type` IN ('전보', '전직', '승진', '직급조정', '입사', '퇴사'))
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 알림 및 공지사항
-- 알림
CREATE TABLE `notice` (
    `notice_id`      BIGINT NOT NULL AUTO_INCREMENT,
    `notice_content` TEXT NOT NULL,
    `notice_type`    VARCHAR(255) NOT NULL,
    `is_read`        BOOLEAN NOT NULL DEFAULT FALSE,
    `employee_id`    BIGINT       NOT NULL,
    PRIMARY KEY (`notice_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`)
    # type check 필요
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- 공지사항
CREATE TABLE `board` (
    `board_id`      BIGINT NOT NULL AUTO_INCREMENT,
    `board_title`   VARCHAR(255) NOT NULL,
    `board_content` VARCHAR(255) NOT NULL,
    `board_created_at` DATETIME NOT NULL,
    `board_file`    VARCHAR(255),
    `board_file_size` BIGINT,
    `employee_id`   BIGINT NOT NULL,
    PRIMARY KEY (`board_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

