package com.ddis.ddis_hr.payroll.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalaryDetailDTO {
    private Long salaryId;
    private Long employeeId;
    private String employeeName;

    private String headName;
    private String departmentName;
    private String rankName;
    private String teamName;

    private String salaryDate; // YYYY-MM

    // 지급 항목
    private int salaryBasic;
    private int salaryOvertime;
    private int salaryNight;
    private int salaryHoliday;
    private int salaryMeal;
    private int salaryTransport;
    private int salaryBonus;

    // 공제 항목
    private int incomeTax;
    private int localIncomeTax;
    private int nationalPension;
    private int employmentInsurance;
    private int healthInsurance;
    private int longCareInsurance;

    // 합계
    private int totalIncome;
    private int totalDeductions;
    private int netSalary;
}
