package com.ddis.ddis_hr.payroll.command.application.controller;

import com.ddis.ddis_hr.payroll.command.application.dto.RetirementMailRequestDTO;
import com.ddis.ddis_hr.payroll.command.application.service.RetirementMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retirement")
@RequiredArgsConstructor
public class RetirementMailController {

    private final RetirementMailService retirementMailService;

    @PostMapping("/mail")
    public ResponseEntity<String> sendMail(@RequestBody RetirementMailRequestDTO dto) {
        retirementMailService.sendRetirementByEmail(dto);
        return ResponseEntity.ok("퇴직금 명세서 메일 전송 완료");
    }
}