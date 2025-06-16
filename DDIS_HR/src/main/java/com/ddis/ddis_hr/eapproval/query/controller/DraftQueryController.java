package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;
import com.ddis.ddis_hr.eapproval.query.service.DraftQueryService;
import com.ddis.ddis_hr.eapproval.query.service.ReceiverDocService;
import com.ddis.ddis_hr.eapproval.query.service.ReferenceDocService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drafts/query")
@RequiredArgsConstructor
public class DraftQueryController {

    private final DraftQueryService draftQueryService;
    private final ReferenceDocService referenceDocService;
    private final ReceiverDocService receiverDocService;

    // 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<DraftDetailResponseQueryDTO> getDraftDetail(@PathVariable("id") Long docId) {
        DraftDetailResponseQueryDTO dto = draftQueryService.getDraftDetail(docId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<DraftDTO>> getMyDrafts(
            @AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        List<DraftDTO> dtos = draftQueryService.getMyDrafts(employeeId);
        return ResponseEntity.ok(dtos);
    }
    @PostMapping("/{docId}/recall")
    public ResponseEntity<Void> recall(@PathVariable Long docId) {
        draftQueryService.recallDocument(docId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reference")
    public List<ReferenceDocDTO> getReferenceDocs(@AuthenticationPrincipal CustomUserDetails user) {
        return referenceDocService.getReferenceDocsByEmployeeId(user.getEmployeeId());
    }

    // 수신함 (요청하신 부분)
    @GetMapping("/receiver")
    public List<ReferenceDocDTO> getReceiverDocs(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return receiverDocService.getReceiverDocsByEmployeeId(userDetails.getEmployeeId());
    }

    @PatchMapping("/reference/{docId}/read")
    public void markReferenceDocAsRead(@PathVariable Long docId, @AuthenticationPrincipal CustomUserDetails user) {
        referenceDocService.markAsRead(user.getEmployeeId(), docId);
    }

}
