package com.ddis.ddis_hr.employee.command.application.service;

import com.ddis.ddis_hr.employee.command.application.dto.EmployeeEnrollDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeHrUpdateDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeUpdateDTO;


public interface EmployeeService {

    Long enrollEmployee(EmployeeEnrollDTO dto);

    void updateEmployee(Long employeeId, EmployeeUpdateDTO dto);

    void hrUpdateEmployee(Long employeeId, EmployeeHrUpdateDTO dto);
}
