package com.ddis.ddis_hr.employee.command.application.controller;

import com.ddis.ddis_hr.employee.command.application.dto.EmployeeEnrollDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeHrUpdateDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeUpdateDTO;
import com.ddis.ddis_hr.employee.command.application.service.EmployeeService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 사원 등록
    @PostMapping("/enroll")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Long> enroll(
            @RequestBody EmployeeEnrollDTO dto

    ) {
        Long id = employeeService.enrollEmployee(dto);
        return ResponseEntity.ok(id);
    }

    // 본인 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @RequestBody EmployeeUpdateDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        if (!user.getEmployeeId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        employeeService.updateEmployee(id, dto);
        return ResponseEntity.ok().build();
    }

    //사원 정보 수정
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Void> adminUpdate(
            @PathVariable Long id,
            @RequestBody EmployeeHrUpdateDTO dto
    ) {
        employeeService.hrUpdateEmployee(id, dto);
        return ResponseEntity.ok().build();
    }

    //사원 정보 삭제
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('HR')")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        employeeService.deleteEmployee(id);
//        return ResponseEntity.noContent().build();
//    }
}
