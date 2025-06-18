package com.ddis.ddis_hr.employee.command.application.controller;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeEnrollDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeHrUpdateDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeUpdateDTO;
import com.ddis.ddis_hr.employee.command.application.service.EmployeeService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 사원 등록 REST API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final S3Service s3Service;

    public EmployeeController(EmployeeService employeeService,
                              S3Service s3Service) {
        this.employeeService = employeeService;
        this.s3Service = s3Service;
    }

    // 사원 등록 API
    @PostMapping("/enroll")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Long> enroll(
            @RequestBody EmployeeEnrollDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long id = employeeService.enrollEmployee(dto);
        return ResponseEntity.ok(id);
    }

    // 사원 정보 수정 (일반 사원) API
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @RequestBody EmployeeUpdateDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        // 본인 확인
        if (!user.getEmployeeId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        employeeService.updateEmployee(id, dto);
        return ResponseEntity.ok().build();
    }

    //사원 정보 수정 (인사팀) API
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Void> adminUpdate(
            @PathVariable Long id,
            @RequestBody EmployeeHrUpdateDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        employeeService.hrUpdateEmployee(id, dto);
        return ResponseEntity.ok().build();
    }
}
