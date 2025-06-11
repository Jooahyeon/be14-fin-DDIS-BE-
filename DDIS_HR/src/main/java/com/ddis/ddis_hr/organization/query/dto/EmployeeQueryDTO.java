package com.ddis.ddis_hr.organization.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeQueryDTO {
    private Long employeeId;
    private String employeeName;
    private String positionCode;
    private String positionName;
    private String rankCode;
    private String rankName;
    private String jobCode;
    private String jobName;
    private Long headId;
    private Long departmentId;
    private Long teamId;
    private String birthdate;
    private String email;
}
