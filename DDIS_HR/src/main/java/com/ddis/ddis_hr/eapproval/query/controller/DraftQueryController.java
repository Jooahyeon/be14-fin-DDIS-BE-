package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;
import com.ddis.ddis_hr.eapproval.query.service.DraftDetailService;
import com.ddis.ddis_hr.eapproval.query.service.ReceiverDocService;
import com.ddis.ddis_hr.eapproval.query.service.ReferenceDocService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 상세 조회 컨트롤러

@RestController
@RequestMapping("/drafts/query")
@RequiredArgsConstructor
public class DraftQueryController {

    private final DraftDetailService draftDetailService;
    private final ReferenceDocService referenceDocService;
    private final ReceiverDocService receiverDocService;

    // 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<DraftDetailResponseQueryDTO> getDraftDetail(@PathVariable("id") Long docId) {
        DraftDetailResponseQueryDTO dto = draftDetailService.getDraftDetail(docId);
        return ResponseEntity.ok(dto);
    }

    // 회수
    @PostMapping("/{docId}/recall")
    public ResponseEntity<Void> recall(@PathVariable Long docId) {
        draftDetailService.recallDocument(docId);
        return ResponseEntity.ok().build();
    }

    // 참조함 조회
    @GetMapping("/reference")
    public List<ReferenceDocDTO> getReferenceDocs(@AuthenticationPrincipal CustomUserDetails user) {
        return referenceDocService.getReferenceDocsByEmployeeId(user.getEmployeeId());
    }

    // 수신함 조회
    @GetMapping("/receiver")
    public List<ReferenceDocDTO> getReceiverDocs(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return receiverDocService.getReceiverDocsByEmployeeId(userDetails.getEmployeeId());
    }

    // 읽음여부
    @PatchMapping("/reference/{docId}/read")
    public void markReferenceDocAsRead(@PathVariable Long docId, @AuthenticationPrincipal CustomUserDetails user) {
        referenceDocService.markAsRead(user.getEmployeeId(), docId);
    }

}
