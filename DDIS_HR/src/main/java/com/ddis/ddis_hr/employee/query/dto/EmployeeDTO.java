package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Alias("MemberEmployeeDTO") // member 패키지
public class EmployeeDTO {
    private Long employeeId;
    private String employeeName;
    private String employeePwd;
    private String employeeProfile;
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

    // Optionally: 조인된 이름 정보도 추가 가능
    private String positionName;
    private String rankName;
    private String jobName;
    private String headName;
    private String departmentName;
    private String teamName;
}