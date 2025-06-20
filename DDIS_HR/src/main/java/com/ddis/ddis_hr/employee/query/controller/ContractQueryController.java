package com.ddis.ddis_hr.employee.query.controller;

import com.ddis.ddis_hr.employee.query.dto.ContractListDTO;
import com.ddis.ddis_hr.employee.query.service.ContractQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractQueryController {

    private final ContractQueryService contractQueryService;

    @Autowired
    public ContractQueryController(ContractQueryService contractQueryService) {
        this.contractQueryService = contractQueryService;
    }

    // 계약 목록 조회
    @PreAuthorize("hasRole('HR')")
    @GetMapping
    public ResponseEntity<List<ContractListDTO>> list() {
        return ResponseEntity.ok(contractQueryService.findAll());
    }

    // 사번별 계약 목록 조회
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ContractListDTO>> listByEmployee(
            @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(contractQueryService.findByEmployeeId(employeeId));
    }

}
