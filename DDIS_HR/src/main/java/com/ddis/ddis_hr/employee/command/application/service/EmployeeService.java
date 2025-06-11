package com.ddis.ddis_hr.employee.command.application.service;

import com.ddis.ddis_hr.employee.command.application.dto.EmployeeEnrollDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeHrUpdateDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeUpdateDTO;


public interface EmployeeService {

    Long enrollEmployee(EmployeeEnrollDTO dto);                             // 사원 등록

    void updateEmployee(Long employeeId, EmployeeUpdateDTO dto);            // 사원 정보 수정 (일반 사원)

//    void hrUpdateEmployee(Long employeeId, EmployeeHrUpdateDTO dto);     // 사원 정보 수정 (인사팀)
}
