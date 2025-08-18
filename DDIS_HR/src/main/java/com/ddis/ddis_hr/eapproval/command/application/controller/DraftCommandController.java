package com.ddis.ddis_hr.eapproval.command.application.controller;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateResponseCommandDTO;
import com.ddis.ddis_hr.eapproval.command.application.service.ApprovalWorkflowService;
import com.ddis.ddis_hr.eapproval.command.application.service.DraftCommandService;
import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLine;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DraftDocument;
import com.ddis.ddis_hr.eapproval.command.domain.repository.ApprovalLineRepository;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DraftRepository;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.TempSaveResDTO;
import com.ddis.ddis_hr.eapproval.query.service.DraftDetailService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

// 기안 생성/조회/상신
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/drafts")
public class DraftCommandController {

    private final ApprovalLineRepository approvalLineRepo;
    private final ApprovalWorkflowService workflow;
    private final DraftCommandService draftCommandService;
    private final DraftDetailService draftDetailService;


    @PostMapping("/creation")
    public ResponseEntity<DraftCreateResponseCommandDTO> createDraft(
            @RequestBody DraftCreateCommandDTO dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        log.debug("⏺️ createDraft 호출, DTO = {}", dto);


        // 1. 기안자 정보 주입
        dto.setEmployeeId(user.getEmployeeId());

        // 2. 기안 + 결재라인 저장
        DraftDocument draftDocumentEntity = draftCommandService.saveDraftAndLines(dto);

        // 3. 워크플로우 초기화
        workflow.initializeWorkflow(draftDocumentEntity);

        // 4. 대표 결재라인 가져오기
        ApprovalLine firstLine = approvalLineRepo.findByDocIdAndStep(draftDocumentEntity.getDocId(), 1)
                .orElseThrow(() -> new IllegalStateException("1단계 결재자가 존재하지 않습니다."));

        // 5. 응답 반환
        return ResponseEntity.ok(
                new DraftCreateResponseCommandDTO(draftDocumentEntity.getDocId(), firstLine.getApprovalLineId())
        );
    }
}




