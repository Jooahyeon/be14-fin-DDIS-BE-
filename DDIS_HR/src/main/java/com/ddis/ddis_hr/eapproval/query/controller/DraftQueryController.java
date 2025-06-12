package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.DraftMapper;
import com.ddis.ddis_hr.eapproval.query.service.DraftQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drafts/query")
@RequiredArgsConstructor
public class DraftQueryController {

    private final DraftQueryService draftQueryService;

    @GetMapping("/{docId}")
    public ResponseEntity<DraftDetailResponseQueryDTO> getDraftDetail(@PathVariable Long docId) {
        DraftDetailResponseQueryDTO dto = draftQueryService.getDraftDetail(docId);
        return ResponseEntity.ok(dto);
    }



}
