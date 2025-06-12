package com.ddis.ddis_hr.payroll.query.service;

import com.ddis.ddis_hr.payroll.query.dto.EmployeeSearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.EmployeeSummaryDTO;

import java.util.List;

public interface EmployeeSearchService {
    List<EmployeeSummaryDTO> searchEmployees(EmployeeSearchCondition condition);
    EmployeeSummaryDTO findById(Long employeeId);
}
