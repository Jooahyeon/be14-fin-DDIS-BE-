package com.ddis.ddis_hr.chatbot.command.application.controller;

import com.ddis.ddis_hr.chatbot.command.application.dto.PolicyFileDTO;
import com.ddis.ddis_hr.chatbot.command.application.service.PolicyPdfQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
public class PolicyPdfQueryController {

    private final PolicyPdfQueryService policyPdfQueryService;

    @GetMapping("/files")
    public ResponseEntity<List<PolicyFileDTO>> listUploadedFiles() {
        return ResponseEntity.ok(policyPdfQueryService.listUploadedPolicyFiles());
    }
}
