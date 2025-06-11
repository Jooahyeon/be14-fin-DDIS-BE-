package com.ddis.ddis_hr.employee.command.application.controller;

import com.ddis.ddis_hr.employee.command.application.dto.EmployeeEnrollDTO;
import com.ddis.ddis_hr.employee.command.application.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사원 등록 REST API 컨트롤러
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 사원 등록 API 구현
    @PostMapping("/enroll")
    public ResponseEntity<Long> enroll(@RequestBody EmployeeEnrollDTO dto) {
        Long id = employeeService.enrollEmployee(dto);
        return ResponseEntity.ok(id);
    }
}
