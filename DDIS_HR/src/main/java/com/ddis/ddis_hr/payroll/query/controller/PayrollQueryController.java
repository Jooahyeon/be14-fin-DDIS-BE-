package com.ddis.ddis_hr.payroll.query.controller;

import com.ddis.ddis_hr.payroll.query.dto.*;
import com.ddis.ddis_hr.payroll.query.service.EmployeeSearchService;
import com.ddis.ddis_hr.payroll.query.service.SalaryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<EmployeeSummaryDTO>> getFilteredEmployees(@ModelAttribute EmployeeSearchCondition condition) {
        List<EmployeeSummaryDTO> result = employeeSearchService.searchEmployees(condition);
        return ResponseEntity.ok(result);
    }

    // 특정 사원의 인사정보 (급여에서 필요한 것만)
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeSummaryDTO> getEmployeeById(@PathVariable Long employeeId) {
        EmployeeSummaryDTO dto = employeeSearchService.findById(employeeId);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }

//    @PreAuthorize("hasRole('HR')")
    @GetMapping("/salaries")
    public ResponseEntity<List<SalarySummaryDTO>> getSalarySummaries(@ModelAttribute SalarySearchCondition condition) {
        List<SalarySummaryDTO> result = salaryQueryService.getSalarySummaries(condition);
        return ResponseEntity.ok(result);
    }

    // 사원별 급여상세
    @GetMapping("/salaries/{employeeId}")
    public ResponseEntity<SalaryDetailDTO> getSalaryDetail(@PathVariable Long employeeId,
                                                           @RequestParam YearMonth month) {
        SalaryDetailDTO dto = salaryQueryService.getSalaryDetail(employeeId, month);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }
}