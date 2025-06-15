package com.ddis.ddis_hr.employee.command.application.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeHrUpdateDTO {
    private String employeeName;
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
    private String workType;
    private String bankName;
    private String bankDepositor;
    private String bankAccount;
    private String isDisorder;
    private String militaryType;
    private String isMarriage;
    private LocalDate marriageDate;
    private Integer familyCount;
    private Integer careerYearCount;
    private String previousCompany;
    private String finalAcademic;
    private String employeeSchool;
    private String employeeDept;
    private LocalDate graduationYear;
    private String isFourInsurances;
    private Long positionId;
    private Long rankId;
    private Long jobId;
    private Long headId;
    private Long departmentId;
    private Long teamId;
}

