package com.ddis.ddis_hr.payroll.query.controller;

import com.ddis.ddis_hr.payroll.query.dto.*;
import com.ddis.ddis_hr.payroll.query.service.RetirementQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR')")
@RequestMapping("/payroll")
public class RetirementQueryController {

    private final RetirementQueryService retirementQueryService;

//    @PreAuthorize("hasRole('HR')")
    @GetMapping("/retirements")
    public ResponseEntity<List<RetirementSummaryDTO>> getRetirementList(@ModelAttribute RetirementSearchCondition condition) {
        List<RetirementSummaryDTO> result = retirementQueryService.getSummaries(condition);
        return ResponseEntity.ok(result);
    }

//    @PreAuthorize("hasRole('HR')")
    @GetMapping("/retirements/{employeeId}")
    public ResponseEntity<RetirementDetailDTO> getDetail(@PathVariable Long employeeId) {
        RetirementDetailDTO dto = retirementQueryService.getDetail(employeeId);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }
}
