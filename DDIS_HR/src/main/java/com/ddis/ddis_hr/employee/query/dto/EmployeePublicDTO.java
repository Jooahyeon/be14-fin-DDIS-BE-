package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Alias("EmployeePublicDTO")
public class EmployeePublicDTO {
    private Long employeeId;
    private String employeeName;
    private String employeePhotoName;
    private String employeePhotoUrl;
    private String employeeContact;
    private String employeeEmail;
    private String workType;
    private LocalDate employmentDate;
    private LocalDate retirementDate;

    // Optionally: 조인된 이름 정보도 추가 가능
    private String positionName;
    private String rankName;
    private String jobName;
    private String headName;
    private String departmentName;
    private String teamName;
}
