package com.ddis.ddis_hr.payroll.command.application.controller;

import com.ddis.ddis_hr.payroll.command.application.dto.PayrollMailRequestDTO;
import com.ddis.ddis_hr.payroll.command.application.service.PayrollMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll")
@RequiredArgsConstructor
public class PayrollMailController {

    private final PayrollMailService payrollMailService;

    @PreAuthorize("hasAnyRole('HR')")
    @PostMapping("/mail")
    public ResponseEntity<String> sendMail(@RequestBody PayrollMailRequestDTO dto) {
        payrollMailService.sendPaystubByEmail(dto);
        return ResponseEntity.ok("메일 전송 완료");
    }
}
