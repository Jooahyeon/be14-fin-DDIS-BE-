package com.ddis.ddis_hr.employee.command.application.controller;

import com.ddis.ddis_hr.employee.command.application.dto.ContractEnrollDTO;
import com.ddis.ddis_hr.employee.command.application.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    // 계약 등록
    @PostMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Long> create(@RequestBody ContractEnrollDTO dto) {
        Long id = contractService.createContract(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    // 계약 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}

