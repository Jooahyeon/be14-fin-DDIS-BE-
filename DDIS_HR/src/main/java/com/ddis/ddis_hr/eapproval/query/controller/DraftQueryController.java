package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.DraftQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    // 기안함 ( 추후 ApprovalDcoumentQueryController랑 병합)
//    @GetMapping()
//    public ResponseEntity<List<DraftDTO>> getMyDrafts(
//            @AuthenticationPrincipal CustomUserDetails user) {
//
//        Long employeeId = user.getEmployeeId();
//        List<DraftDTO> drafts = draftQueryService.getMyDrafts(employeeId);
//        return ResponseEntity.ok(drafts);
//    }



}
