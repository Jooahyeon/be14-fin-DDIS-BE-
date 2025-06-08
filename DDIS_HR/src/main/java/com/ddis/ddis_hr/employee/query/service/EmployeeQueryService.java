package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dto.EmployeeDTO;

public interface EmployeeQueryService {
    EmployeeDTO findByMyId(Long employeeId);
}
