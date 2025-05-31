package com.ddis.ddis_hr.payroll.query.dto;

import lombok.*;

import java.time.YearMonth;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalarySearchCondition {
    private String keyword;        // 사번 or 성명
    private String headName;
    private String departmentName;
    private String teamName;
    private String rankName;

    private YearMonth fromMonth;  // 시작 월
    private YearMonth toMonth;    // 종료 월
}