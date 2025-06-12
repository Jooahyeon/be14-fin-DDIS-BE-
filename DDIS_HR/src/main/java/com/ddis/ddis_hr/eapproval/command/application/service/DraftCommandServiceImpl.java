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
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DraftCommandServiceImpl implements DraftCommandService {

    private final DraftRepository draftRepository;
    private final ApprovalLineCommandService approvalLineCommandService;
    private final DocumentBoxRepository documentBoxRepository;
    private final ObjectMapper objectMapper;  // Spring Bean 으로 등록되어 있어야 합니다.

    @Transactional
    @Override
    public DraftCreateResponseCommandDTO createDraft(DraftCreateCommandDTO dto) {
        // 1) Draft 저장 → docId 획득
        Draft savedDraft = draftRepository.save(dto.toEntity());
        Long docId = savedDraft.getDocId();

        // 2) 기안자 저장
        saveDocumentBoxEntry(dto.getEmployeeId(), docId, "기안자");

        // 3) 결재라인 자동 생성 & 결재자 저장
        List<Long> approvalLineIds = null;
        List<ApprovalLineDTO> lines = dto.getApprovalLines();
        if (lines != null && !lines.isEmpty()) {
            approvalLineIds = approvalLineCommandService.saveManualLine(docId, lines, dto.getFormId(), dto.getEmployeeId());
            lines.forEach(line -> saveDocumentBoxEntry(line.getEmployeeId(), docId, "결재자"));
        } else {
            // fallback → 기존 자동 결재선 로직 유지
            Long approvalLineId = approvalLineCommandService.createAutoLine(docId, dto.getEmployeeId());
            approvalLineIds = List.of(approvalLineId);
            List<Long> approvers = dto.getApprovers() != null ? dto.getApprovers() : List.of();
            approvers.forEach(empId -> saveDocumentBoxEntry(empId, docId, "결재자"));
        }
        // 4) 협조자 저장 (DocumentBox만)
        List<Long> cooperators = dto.getCooperators() != null ? dto.getCooperators() : List.of();
        cooperators.forEach(empId -> saveDocumentBoxEntry(empId, docId, "협조자"));

        // 5) 수신자 저장 (DocumentBox만)
        List<Long> receivers = dto.getReceivers() != null ? dto.getReceivers() : List.of();
        receivers.forEach(empId -> saveDocumentBoxEntry(empId, docId, "수신자"));

        // 6) 참조자 저장 (DocumentBox만)
        List<Long> ccs = dto.getCcs() != null ? dto.getCcs() : List.of();
        ccs.forEach(empId -> saveDocumentBoxEntry(empId, docId, "참조자"));

        Long representativeLineId = (approvalLineIds != null && !approvalLineIds.isEmpty())
                ? approvalLineIds.get(0)
                : null;

        // 7) 응답 반환
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
}

