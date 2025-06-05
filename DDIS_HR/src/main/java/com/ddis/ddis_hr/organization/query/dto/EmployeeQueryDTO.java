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
    private Long employeeId;       // JSON: "employeeId"
    private String employeeName;   // JSON: "employeeName"
    private String positionName;   // JSON: "positionName"
    private String rankName;       // JSON: "rankName"
    private Long headId;           // JSON: "headId"
    private Long departmentId;     // JSON: "departmentId"
    private Long teamId;           // JSON: "teamId"
    private String birthdate;      // JSON: "birthdate"
    private String email;          // JSON: "email"
}
