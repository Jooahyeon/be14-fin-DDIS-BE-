package com.ddis.ddis_hr.payroll.query.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeSummaryDTO {
    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
    private String headName;
    private String departmentName;
    private String teamName;
    private String rankName;
    private LocalDate employmentDate;
    private String bankName;
    private String bankAccount;
    private String bankDepositor;
}
