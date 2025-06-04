package com.ddis.ddis_hr.payroll.command.application.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
public class PayrollMailRequestDTO {
    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private String departmentName;
    private String headName;
    private String teamName;
    private String rankName;
    private String salaryDate;
    private Integer netSalary;

    private List<PayItem> pays;
    private List<PayItem> deductions;
}