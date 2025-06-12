package com.ddis.ddis_hr.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_name", nullable = false, length = 255)
    private String employeeName;

    @Column(name = "employee_pwd", nullable = false, length = 255)
    private String employeePwd;

    @Column(name = "employee_nation", nullable = false, length = 255)
    private String employeeNation;

    @Column(name = "employee_gender", nullable = false, length = 255)
    private String employeeGender;

    @Column(name = "employee_birth", nullable = false)
    private LocalDateTime employeeBirth;

    @Column(name = "employee_resident", nullable = false, length = 255)
    private String employeeResident;

    @Column(name = "employee_contact", nullable = false, length = 255)
    private String employeeContact;

    @Column(name = "employee_email", nullable = false, length = 255)
    private String employeeEmail;

    @Column(name = "employee_address", nullable = false, length = 255)
    private String employeeAddress;

    @Column(name = "employment_date", nullable = false)
    private LocalDateTime employmentDate;

    @Column(name = "retirement_date")
    private LocalDateTime retirementDate;

    @Column(name = "work_type", nullable = false)
    private Boolean workType;

    @Column(name = "bank_name", nullable = false, length = 255)
    private String bankName;

    @Column(name = "bank_depositor", nullable = false, length = 255)
    private String bankDepositor;

    @Column(name = "bank_account", nullable = false, length = 255)
    private String bankAccount;

    @Column(name = "is_disorder", nullable = false)
    private Boolean isDisorder;

    @Column(name = "military_type", nullable = false, length = 255)
    private String isMilitary;

    @Column(name = "is_marriage", nullable = false)
    private Boolean isMarriage;

    @Column(name = "marriage_date")
    private LocalDateTime marriageDate;

    @Column(name = "family_count")
    private Integer familiyCount;

    @Column(name = "career_year_count")
    private Integer careerYearCount;

    @Column(name = "previous_company", length = 255)
    private String previousCompany;

    @Column(name = "final_academic", nullable = false, length = 255)
    private String finalAcademic;

    @Column(name = "employee_school", nullable = false, length = 255)
    private String employeeSchool;

    @Column(name = "employee_dept", length = 255)
    private String employeeDept;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    @Column(name = "is_four_insurances", nullable = false)
    private Boolean isFourInsurances;

    // Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", nullable = false)
    private Rank rank;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_id", nullable = false)
    private Headquarters headquarters;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;




}
