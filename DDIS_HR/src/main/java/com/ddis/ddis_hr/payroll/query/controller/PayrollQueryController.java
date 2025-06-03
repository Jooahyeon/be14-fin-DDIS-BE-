package com.ddis.ddis_hr.payroll.query.controller;

import com.ddis.ddis_hr.payroll.query.dto.*;
import com.ddis.ddis_hr.payroll.query.service.EmployeeSearchService;
import com.ddis.ddis_hr.payroll.query.service.SalaryQueryService;
import lombok.extern.slf4j.Slf4j;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payroll")
public class PayrollQueryController {

    private final EmployeeSearchService employeeSearchService;
    private final SalaryQueryService salaryQueryService;

    // 🔐 인사팀만 접근 가능
//    @PreAuthorize("hasRole('HR')")
    @GetMapping("/employees")
    public List<EmployeeSummaryDTO> getFilteredEmployees(@ModelAttribute EmployeeSearchCondition condition) {
        return employeeSearchService.searchEmployees(condition);
    }

    // 특정 사원의 인사정보 (급여에서 필요한 것만)
    @GetMapping("/employees/{employeeId}")
    public EmployeeSummaryDTO getEmployeeById(@PathVariable Long employeeId) {
        return employeeSearchService.findById(employeeId);
    }

//    @PreAuthorize("hasRole('HR')")
    //
    @GetMapping("/salaries")
    public List<SalarySummaryDTO> getSalarySummaries(@ModelAttribute SalarySearchCondition condition) {
        return salaryQueryService.getSalarySummaries(condition);
    }

    // 사원별 급여상세
    @GetMapping("/salaries/{employeeId}")
    public SalaryDetailDTO getSalaryDetail(@PathVariable Long employeeId,
                                           @RequestParam YearMonth month) {
//        log.info("급여명세서 조회: 사번={}, 월={}", employeeId, month); // 추가

        return salaryQueryService.getSalaryDetail(employeeId, month);
    }

}