package com.ddis.ddis_hr.employee.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchDTO {
    private Long   employeeId;
    private String employeeName;
}
