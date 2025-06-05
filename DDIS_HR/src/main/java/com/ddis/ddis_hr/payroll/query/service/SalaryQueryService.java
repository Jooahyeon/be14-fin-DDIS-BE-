package com.ddis.ddis_hr.payroll.query.service;

import com.ddis.ddis_hr.payroll.query.dao.SalaryMapper;
import com.ddis.ddis_hr.payroll.query.dto.SalaryDetailDTO;
import com.ddis.ddis_hr.payroll.query.dto.SalarySearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.SalarySummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryQueryService {

    private final SalaryMapper salaryMapper;

    public List<SalarySummaryDTO> getSalarySummaries(SalarySearchCondition condition) {
        return salaryMapper.searchSalarySummaries(condition);
    }

    public SalaryDetailDTO getSalaryDetail(Long employeeId, YearMonth month) {
        return salaryMapper.findSalaryDetail(employeeId, month.toString()); // "2025-05"
    }

}