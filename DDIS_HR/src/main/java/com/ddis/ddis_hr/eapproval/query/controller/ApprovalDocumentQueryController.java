package com.ddis.ddis_hr.eapproval.query.controller;


import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.service.ApprovalDocumentQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalDocumentQueryController {

    private final ApprovalDocumentQueryService approvalDocumentQueryService;

    @GetMapping
    public ResponseEntity<List<DocumentDTO>> getPendingApprovals(@AuthenticationPrincipal CustomUserDetails user) {
        // Assuming principal.getName() returns employeeId as string
        Long employeeId = user.getEmployeeId();
        List<DocumentDTO> documents = approvalDocumentQueryService.getPendingApprovals(employeeId);
        return ResponseEntity.ok(documents);
    }
}

