package com.ddis.ddis_hr.payroll.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SalaryDTO {
    private Long salaryId;
    private Long employeeId;
    private LocalDateTime salaryDate;

    private int salaryBasic;
    private int salaryOvertime;
    private int salaryNight;
    private int salaryHoliday;
    private int salaryMeal;
    private int salaryTransport;
    private int salaryBonus;

    private int incomeTax;
    private int localIncomeTax;
    private int nationalPension;
    private int employmentInsurance;
    private int healthInsurance;
    private int longCareInsurance;

    private int totalIncome;
    private int totalDeductions;
    private int netSalary;
}
