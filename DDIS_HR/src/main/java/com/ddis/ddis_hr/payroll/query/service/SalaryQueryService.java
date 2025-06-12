package com.ddis.ddis_hr.payroll.query.service;

import com.ddis.ddis_hr.payroll.query.dto.SalaryDetailDTO;
import com.ddis.ddis_hr.payroll.query.dto.SalarySearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.SalarySummaryDTO;

import java.time.YearMonth;
import java.util.List;

public interface SalaryQueryService {
    List<SalarySummaryDTO> getSalarySummaries(SalarySearchCondition condition);
    SalaryDetailDTO getSalaryDetail(Long employeeId, YearMonth month);
}
