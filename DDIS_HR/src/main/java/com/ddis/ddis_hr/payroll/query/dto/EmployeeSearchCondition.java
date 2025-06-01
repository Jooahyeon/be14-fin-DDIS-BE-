package com.ddis.ddis_hr.payroll.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeSearchCondition {
    private String keyword;        // 사번 또는 성명 (LIKE 검색)
    private String headName;       // 본부
    private String departmentName; // 부서
    private String teamName;       // 팀
    private String rankName;       // 직급
}
