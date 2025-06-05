package com.ddis.ddis_hr.payroll.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RetirementMailRequestDTO {

    // 사원 정보
    private Long employeeId;
    private String employeeName;
    private String employeeEmail;     // <-- employee 테이블에서 가져올 것
    private String headName;
    private String departmentName;
    private String teamName;
    private String rankName;

    // 근속 정보
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate employmentDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate retirementDate;
    private Integer totalWorkDays;
    private Integer totalYears;

    // 월별 급여
    private String month1;
    private Integer month1Salary;
    private String month2;
    private Integer month2Salary;
    private String month3;
    private Integer month3Salary;
    private String month4;           // optional
    private Integer month4Salary;    // optional

    private Integer totalBonus;
    private Integer totalDays;       // 3개월 일수
    private Integer averageDailySalary;
    private Integer retireTotal;     // 총 지급액
    private Integer retireIncomeTax; // 소득세
    private Integer provisionActual; // 실 수령액
    private String provisionDate;

    private List<MonthlySalaryDTO> monthlySalaries;

}
