package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.ApprovalLineDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateResponseCommandDTO;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentBox;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentBoxId;
import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DocumentBoxRepository;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DraftRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class DraftCommandServiceImpl implements DraftCommandService {

    private final DraftRepository draftRepository;
    private final ApprovalLineCommandService approvalLineCommandService;
    private final DocumentBoxRepository documentBoxRepository;
    private final ObjectMapper objectMapper;  // Spring Bean 으로 등록되어 있어야 합니다.
    private final ApprovalWorkflowService workflow;

    @Transactional
    @Override
    public DraftCreateResponseCommandDTO createDraft(DraftCreateCommandDTO dto) {
        // 1) Draft 저장 → docId 획득
        Draft savedDraft = draftRepository.save(dto.toEntity());
        Long docId = savedDraft.getDocId();

        // 2) 기안자 저장 (DocumentBox)
        saveDocumentBoxEntry(dto.getEmployeeId(), docId, "기안자");

        // 3) 결재라인 저장 (수동 or 자동)
        List<Long> approvalLineIds = null;
        List<ApprovalLineDTO> lines = dto.getApprovalLines();
        if (lines != null && !lines.isEmpty()) {
            // 3-1) 수동 결재라인 저장
            approvalLineIds = approvalLineCommandService.saveManualLine(docId, lines, dto.getFormId(), dto.getEmployeeId());
            lines.forEach(line -> saveDocumentBoxEntry(line.getEmployeeId(), docId, "결재자"));
        } else {
            // 3-2) 자동 결재라인 생성 및 저장
            Long approvalLineId = approvalLineCommandService.createAutoLine(docId, dto.getEmployeeId());
            approvalLineIds = List.of(approvalLineId);
            List<Long> approvers = dto.getApprovers() != null ? dto.getApprovers() : List.of();
            approvers.forEach(empId -> saveDocumentBoxEntry(empId, docId, "결재자"));
        }

        // 5) 협조자 저장 (DocumentBox만)
        List<Long> cooperators = dto.getCooperators() != null ? dto.getCooperators() : List.of();
        cooperators.forEach(empId -> saveDocumentBoxEntry(empId, docId, "협조자"));

        // 6) 수신자 저장 (DocumentBox만)
        List<Long> receivers = dto.getReceivers() != null ? dto.getReceivers() : List.of();
        receivers.forEach(empId -> saveDocumentBoxEntry(empId, docId, "수신자"));

        // 7) 참조자 저장 (DocumentBox만)
        List<Long> ccs = dto.getCcs() != null ? dto.getCcs() : List.of();
        ccs.forEach(empId -> saveDocumentBoxEntry(empId, docId, "참조자"));

        // 8) 대표 결재라인 ID 추출 (응답 DTO용)
        Long representativeLineId = (approvalLineIds != null && !approvalLineIds.isEmpty())
                ? approvalLineIds.get(0)
                : null;

        // 9) 응답 반환
        return new DraftCreateResponseCommandDTO(docId, representativeLineId);
    }
    /**
     * JSON 배열 노드에서 long 리스트로 변환
     */
    private List<Long> parseLongList(JsonNode root, String fieldName) {
        JsonNode arr = root.path(fieldName);
        if (!arr.isArray()) {
            return List.of();
        }
        List<Long> list = new ArrayList<>();
        for (JsonNode node : arr) {
            if (node.canConvertToLong()) {
                list.add(node.asLong());
            }
        }
        return list;
    }

    /**
     * Builder 로 DocumentBox 엔티티 생성 후 저장
     */
    private void saveDocumentBoxEntry(Long employeeId, Long docId, String role) {
        DocumentBox box = DocumentBox.builder()
                .id(new DocumentBoxId(employeeId, docId))
                .role(role)
                .isRead(false)
                .isDeleted(false)
                .build();

        documentBoxRepository.save(box);
    }




    @Transactional
    @Override
    public Draft saveDraftAndLines(DraftCreateCommandDTO dto) {
        Draft savedDraft = draftRepository.save(dto.toEntity());
        Long docId = savedDraft.getDocId();

        saveDocumentBoxEntry(dto.getEmployeeId(), docId, "기안자");

        List<ApprovalLineDTO> lines = dto.getApprovalLines();
        if (lines != null && !lines.isEmpty()) {
            approvalLineCommandService.saveManualLine(docId, lines, dto.getFormId(), dto.getEmployeeId());
            lines.forEach(line -> saveDocumentBoxEntry(line.getEmployeeId(), docId, "결재자"));
        } else {
            approvalLineCommandService.createAutoLine(docId, dto.getEmployeeId());
            List<Long> approvers = dto.getApprovers() != null ? dto.getApprovers() : List.of();
            approvers.forEach(empId -> saveDocumentBoxEntry(empId, docId, "결재자"));
        }

        List<Long> cooperators = dto.getCooperators() != null ? dto.getCooperators() : List.of();
        cooperators.forEach(empId -> saveDocumentBoxEntry(empId, docId, "협조자"));

        List<Long> receivers = dto.getReceivers() != null ? dto.getReceivers() : List.of();
        receivers.forEach(empId -> saveDocumentBoxEntry(empId, docId, "수신자"));

        List<Long> ccs = dto.getCcs() != null ? dto.getCcs() : List.of();
        ccs.forEach(empId -> saveDocumentBoxEntry(empId, docId, "참조자"));

        return savedDraft; // ✅ Draft 반환
    }

}

