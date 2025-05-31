package com.ddis.ddis_hr.payroll.query.controller;

import com.ddis.ddis_hr.payroll.query.dto.*;
import com.ddis.ddis_hr.payroll.query.service.EmployeeSearchService;
import com.ddis.ddis_hr.payroll.query.service.SalaryQueryService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

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

//    @PreAuthorize("hasRole('HR')")
    @GetMapping("/salaries")
    public List<SalarySummaryDTO> getSalarySummaries(@ModelAttribute SalarySearchCondition condition) {
        return salaryQueryService.getSalarySummaries(condition);
    }

    @GetMapping("/salaries/{employeeId}")
    public SalaryDetailDTO getSalaryDetail(@PathVariable Long employeeId,
                                           @RequestParam YearMonth month) {
        return salaryQueryService.getSalaryDetail(employeeId, month);
    }

}