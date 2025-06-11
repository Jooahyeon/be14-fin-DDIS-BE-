package com.ddis.ddis_hr.employee.command.application.service;

import com.ddis.ddis_hr.employee.command.application.dto.EmployeeEnrollDTO;


public interface EmployeeService {

    Long enrollEmployee(EmployeeEnrollDTO dto); // 사원 등록 비즈니스 로직 정의
}
