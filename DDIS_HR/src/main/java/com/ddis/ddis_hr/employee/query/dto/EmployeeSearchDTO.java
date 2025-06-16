package com.ddis.ddis_hr.employee.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사원 검색 결과용 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchDTO {
    private Long   employeeId;
    private String employeeName;
}
