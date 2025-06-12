package com.ddis.ddis_hr.eapproval.command.application.controller;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateResponseCommandDTO;
import com.ddis.ddis_hr.eapproval.command.application.service.DraftCommandService;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.DraftQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drafts")
@RequiredArgsConstructor
public class DraftCommandController {

    private final DraftQueryService draftQueryService;
    private final DraftCommandService draftCommandService;

    /**
     * 기안문 생성 요청
     * @para requestDto JSON 본문(DraftCreateCommandDTO)
     * @return 생성된 문서 ID
     */
//    @PostMapping
//    public ResponseEntity<Long> createDraft(@RequestBody DraftCreateCommandDTO requestDto) {
//        Long docId = draftCommandService.createDraft(requestDto);
//        return ResponseEntity.ok(docId);
//    }

    @PostMapping("/creation")
    public ResponseEntity<DraftCreateResponseCommandDTO> createDraft(
            @RequestBody DraftCreateCommandDTO dto,
            @AuthenticationPrincipal CustomUserDetails user) {

        dto.setEmployeeId(user.getEmployeeId());        // 사번 주입
        DraftCreateResponseCommandDTO resp = draftCommandService.createDraft(dto);
        return ResponseEntity.ok(resp);
    }


    /**
     * 기안문 상세 조회 요청
     * @param docId PathVariable로 전달받은 문서 ID
     * @return DraftDetailResponseQueryDTO
     */
    @GetMapping("/{docId}")
    public ResponseEntity<DraftDetailResponseQueryDTO> getDraftDetail(@PathVariable Long docId) {
        DraftDetailResponseQueryDTO response = draftQueryService.getDraftDetail(docId);
        return ResponseEntity.ok(response);
    }
}

