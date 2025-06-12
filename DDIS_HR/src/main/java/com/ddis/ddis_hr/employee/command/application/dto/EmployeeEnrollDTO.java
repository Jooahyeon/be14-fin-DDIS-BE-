package com.ddis.ddis_hr.employee.command.application.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 사원 등록 요청 정보를 담는 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEnrollDTO {
    private Integer employeeId;
    private String employeeName;
    private String employeePwd;
    private String employeePhotoName;
    private String employeePhotoUrl;
    private String employeeNation;
    private String employeeGender;
    private LocalDate employeeBirth;
    private String employeeResident;
    private String employeeContact;
    private String employeeEmail;
    private String employeeAddress;
    private LocalDate employmentDate;
    private LocalDate retirementDate;
    private Boolean workType;
    private String bankName;
    private String bankDepositor;
    private String bankAccount;
    private Boolean isDisorder;
    private String militaryType;
    private Boolean isMarriage;
    private LocalDate marriageDate;
    private Integer familyCount;
    private Integer careerYearCount;
    private String previousCompany;
    private String finalAcademic;
    private String employeeSchool;
    private String employeeDept;
    private Integer graduationYear;
    private Boolean isFourInsurances;
    private Long positionId;
    private Long rankId;
    private Long jobId;
    private Long headId;
    private Long departmentId;
    private Long teamId;
}
