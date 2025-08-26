package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.service.ApprovalDocumentQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
@Tag(name = "testEapproval")
public class ApprovalDocumentQueryController {

    private final ApprovalDocumentQueryService approvalDocumentQueryService;

    /**
     * 결재함 조회 API
     * @param user  로그인된 사용자의 CustomUserDetails
     * @param tab   조회 탭 (결재, 진행, 완료, 전체)
     */
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

    /**
     * 기안함 조회 API
     * @param user  로그인된 사용자의 CustomUserDetails
     */
    @GetMapping("/draftDoc")
    public ResponseEntity<List<DraftDTO>> getMyDrafts(
            @AuthenticationPrincipal CustomUserDetails user) {

        if (user == null) {
            // 인증이 없는 상태 → 401 또는 빈 리스트
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long employeeId = user.getEmployeeId();
        List<DraftDTO> drafts = approvalDocumentQueryService.getMyDrafts(employeeId);
        return ResponseEntity.ok(drafts);
    }

}
