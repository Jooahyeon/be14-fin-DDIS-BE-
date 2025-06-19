package com.ddis.ddis_hr.employee.query.controller;

import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import com.ddis.ddis_hr.employee.query.service.DisciplinaryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinary")
public class DisciplinaryQueryController {

    private final DisciplinaryQueryService disciplinaryQueryService;

    @Autowired
    public DisciplinaryQueryController(DisciplinaryQueryService disciplinaryQueryService) {
        this.disciplinaryQueryService = disciplinaryQueryService;
    }

    // 계약 목록 조회
    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<DisciplinaryListDTO>> list() {
        return ResponseEntity.ok(disciplinaryQueryService.findAll());
    }

    // 사원별 계약 목록 조회
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<DisciplinaryListDTO>> listByEmployee(
            @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(disciplinaryQueryService.findByEmployeeId(employeeId));
    }
}
