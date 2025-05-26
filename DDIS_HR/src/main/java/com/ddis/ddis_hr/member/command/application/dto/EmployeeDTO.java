package com.ddis.ddis_hr.member.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {

    private Long employeeId;
    private String employeeName;
    private String employeePwd;
    private String employeeProfile;
    private String employeeNation;
    private String employeeGender;
    private LocalDateTime employeeBirth;
    private String employeeResident;
    private String employeeContact;
    private String employeeEmail;
    private String employeeAddress;
    private LocalDateTime employmentDate;
    private LocalDateTime returementDate;
    private Boolean workType;
    private String bankName;
    private String bankDepositor;
    private String bankAccount;
    private Boolean isDisorder;
    private String isMilitary;
    private Boolean isMarriage;
    private LocalDateTime marriageDate;
    private Integer familiyCount;
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
