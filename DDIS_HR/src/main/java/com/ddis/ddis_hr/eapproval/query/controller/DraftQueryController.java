package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.DraftQueryService;
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

    // 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<DraftDetailResponseQueryDTO> getDraftDetail(@PathVariable("id") Long docId) {
        DraftDetailResponseQueryDTO dto = draftQueryService.getDraftDetail(docId);
        return ResponseEntity.ok(dto);
    }

    /**
     * 문서 회수 API
     *
     * @param docId 회수할 문서 ID
     * @return 200 OK (회수 완료)
     */
    @PostMapping("/{docId}/recall")
    public ResponseEntity<Void> recall(@PathVariable Long docId) {
        draftQueryService.recallDocument(docId);
        return ResponseEntity.ok().build();
    }



}
