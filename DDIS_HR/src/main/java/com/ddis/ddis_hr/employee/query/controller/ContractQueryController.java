package com.ddis.ddis_hr.employee.query.controller;

import com.ddis.ddis_hr.employee.query.dto.MyContractDTO;
import com.ddis.ddis_hr.employee.query.service.ContractQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contracts")
public class ContractQueryController {

    private final ContractQueryService contractQueryService;

    @Autowired
    public ContractQueryController(ContractQueryService contractQueryService) {
        this.contractQueryService = contractQueryService;
    }


    // 내 계약서 전체 조회
    @GetMapping("/my")
    public ResponseEntity<List<MyContractDTO>> getMyContracts(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long empId = user.getEmployeeId();
        List<MyContractDTO> contracts = contractQueryService.findByEmployeeId(empId);
        return ResponseEntity.ok(contracts);
    }
}
