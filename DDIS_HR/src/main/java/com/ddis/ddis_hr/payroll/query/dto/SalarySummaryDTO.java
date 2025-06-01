package com.ddis.ddis_hr.payroll.query.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SalarySummaryDTO {
    private Long employeeId;
    private String employeeName;

    private LocalDate salaryDate;

    private int totalIncome;
    private int totalDeductions;
    private int netSalary;
}