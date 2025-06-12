package com.ddis.ddis_hr.payroll.query.dto;

import java.time.LocalDate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RetirementDetailDTO {
    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
    private String headName;
    private String departmentName;
    private String teamName;
    private String rankName;
    private LocalDate employmentDate;
    private LocalDate retirementDate;
    private int totalWorkDays;
    private int totalYears;
    private int totalDays;
    private int averageDailySalary;
    private int totalBonus;
    private String month1, month2, month3, month4;
    private int month1Salary, month2Salary, month3Salary, month4Salary;
    private int retireTotal;
    private int retireIncomeTax;
    private int provisionActual;
    private String provisionSituation;
    private LocalDate provisionDate;
    private String remark;
}