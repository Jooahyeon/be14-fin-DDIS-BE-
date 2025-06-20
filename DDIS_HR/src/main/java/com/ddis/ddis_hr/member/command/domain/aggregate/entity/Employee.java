package com.ddis.ddis_hr.member.command.domain.aggregate.entity;

import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "EmployeeAggregate")
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;                     // 사원 PK

    @Column(name = "employee_name", nullable = false)
    private String employeeName;                 // 사원 이름

    @Column(name = "employee_pwd", nullable = false)
    private String employeePwd;                  // 비밀번호

    @Column(name = "employee_photo_name")
    private String employeePhotoName;            // 프로필 사진 파일명

    @Column(name = "employee_photo_url",
    columnDefinition = "TEXT")
    private String employeePhotoUrl;             // 프로필 사진 URL

    @Column(name = "employee_nation", nullable = false)
    private String employeeNation;               // 국적

    @Column(name = "employee_gender", nullable = false)
    private String employeeGender;               // 성별

    @Column(name = "employee_birth", nullable = false)
    private LocalDate employeeBirth;             // 생년월일

    @Column(name = "employee_resident", nullable = false)
    private String employeeResident;             // 주민등록번호

    @Column(name = "employee_contact", nullable = false)
    private String employeeContact;              // 연락처

    @Column(name = "employee_email", nullable = false)
    private String employeeEmail;                // 이메일

    @Column(name = "employee_address", nullable = false)
    private String employeeAddress;              // 주소

    @Column(name = "employment_date", nullable = false)
    private LocalDate employmentDate;            // 입사일

    @Column(name = "retirement_date")
    private LocalDate retirementDate;            // 퇴사일

    @Column(name = "work_type", nullable = false)
    private String workType;                    // 재직 여부

    @Column(name = "bank_name", nullable = false)
    private String bankName;                     // 은행명

    @Column(name = "bank_depositor", nullable = false)
    private String bankDepositor;                // 예금주

    @Column(name = "bank_account", nullable = false)
    private String bankAccount;                  // 계좌번호

    @Column(name = "is_disorder", nullable = false)
    private String isDisorder;                  // 장애 여부

    @Column(name = "military_type", nullable = false)
    private String militaryType;                 // 군필 여부

    @Column(name = "is_marriage", nullable = false)
    private String isMarriage;                  // 결혼 여부

    @Column(name = "marriage_date")
    private LocalDate marriageDate;              // 결혼일

    @Column(name = "family_count", nullable = false)
    private Integer familyCount;                 // 가족 수

    @Column(name = "career_year_count")
    private Integer careerYearCount;             // 경력 연 수

    @Column(name = "previous_company")
    private String previousCompany;              // 이전 직장

    @Column(name = "final_academic", nullable = false)
    private String finalAcademic;                // 최종 학력

    @Column(name = "employee_school", nullable = false)
    private String employeeSchool;               // 최종 학교

    @Column(name = "employee_dept")
    private String employeeDept;                 // 전공

    @Column(name = "graduation_year")
    private Integer graduationYear;              // 졸업 연도

    @Column(name = "is_four_insurances", nullable = false)
    private String isFourInsurances;            // 4대 보험 가입 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private PositionEntity position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", nullable = false)
    private RankEntity rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private JobEntity job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_id")
    private HeadquartersEntity headquarters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;
}
