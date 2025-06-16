package com.ddis.ddis_hr.eapproval.query.controller;
// 문서함 (결재 - 진행 - 완료 분기처리)

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.service.ApprovalDocumentQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalDocumentQueryController {

    private final ApprovalDocumentQueryService approvalDocumentQueryService;

//    @GetMapping
//    public ResponseEntity<List<DocumentDTO>> getPendingApprovals(@AuthenticationPrincipal CustomUserDetails user) {
//        // Assuming principal.getName() returns employeeId as string
//        Long employeeId = user.getEmployeeId();
//        List<DocumentDTO> documents = approvalDocumentQueryService.getPendingApprovals(employeeId);
//        return ResponseEntity.ok(documents);
//    }

    // 결재함
    @GetMapping("/ApprovalBox")
    public ResponseEntity<List<DocumentDTO>> getApprovals(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(name = "tab", defaultValue = "결재") String tab
    ) {
        Long employeeId = user.getEmployeeId();
        List<DocumentDTO> documents;

        switch (tab) {
            case "결재":
                documents = approvalDocumentQueryService.getPendingApprovals(employeeId);
                break;
            case "진행":
                documents = approvalDocumentQueryService.getInProgressApprovals(employeeId);
                break;
            case "완료":
                documents = approvalDocumentQueryService.getCompletedApprovals(employeeId);
                break;
            case "전체":
                documents = approvalDocumentQueryService.getAllApprovals(employeeId);
                break;
            default:
                documents = Collections.emptyList();
        }

        return ResponseEntity.ok(documents);
    }


    // 기안함 ( 추후 ApprovalDcoumentQueryController랑 병합)
    @GetMapping("/draftDoc")
    public ResponseEntity<List<DraftDTO>> getMyDrafts(
            @AuthenticationPrincipal CustomUserDetails user) {

        Long employeeId = user.getEmployeeId();
        List<DraftDTO> drafts = approvalDocumentQueryService.getMyDrafts(employeeId);
        return ResponseEntity.ok(drafts);
    }

}

