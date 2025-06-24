package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.eapproval.command.application.dto.ApprovalLineDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateResponseCommandDTO;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentAttachment;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentBox;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentBoxId;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DraftDocument;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DocumentAttachmentRepository;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DocumentBoxRepository;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DraftRepository;
import com.ddis.ddis_hr.notice.command.application.event.NoticeEvent;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DraftCommandServiceImpl implements DraftCommandService {

    private final DraftRepository draftRepository;
    private final ApprovalLineCommandService approvalLineCommandService;
    private final DocumentBoxRepository documentBoxRepository;
    private final S3Service s3Service;
    private final DocumentAttachmentRepository documentAttachmentRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    @Override
    public DraftCreateResponseCommandDTO createDraft(DraftCreateCommandDTO dto) {
        log.info("🟢 createDraft() 호출됨");

        // 1) Draft 저장 → docId 획득
        DraftDocument savedDraftDocument = draftRepository.save(dto.toEntity());
        Long docId = savedDraftDocument.getDocId();
        log.debug("⏺️ createDraft 호출, DTO = {}", dto);

        // 2) 기안자 저장 (DocumentBox)
        saveDocumentBoxEntry(dto.getEmployeeId(), docId, "기안자");

        // 3) 결재라인 저장 (수동 or 자동)
        List<Long> approvalLineIds = null;
        List<ApprovalLineDTO> lines = dto.getApprovalLines();
        if (lines != null && !lines.isEmpty()) {
            // 3-1) 수동 결재라인 저장
            approvalLineIds = approvalLineCommandService.saveManualLine(docId, lines, dto.getEmployeeId());

            // 💡 line의 type 값에 따라 role 지정
            lines.forEach(line -> {
                String role = "결재".equals(line.getType()) ? "결재자"
                        : "협조".equals(line.getType()) ? "협조자"
                        : "결재자"; // 기본값 fallback
                saveDocumentBoxEntry(line.getEmployeeId(), docId, role);
            });

        } else {
            // 3-2) 자동 결재라인 생성 및 저장
            Long approvalLineId = approvalLineCommandService.createAutoLine(docId, dto.getEmployeeId());
            approvalLineIds = List.of(approvalLineId);

            List<Long> approvers = dto.getApprovers() != null ? dto.getApprovers() : List.of();
            approvers.forEach(empId -> saveDocumentBoxEntry(empId, docId, "결재자")); // 자동라인은 일괄 결재자로 간주
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

        // 8) 첨부파일 메타 저장
        List<String> keys  = dto.getAttachmentKeys();         // S3에서 발급된 키 목록
        List<String> names = dto.getOriginalFileNames();      // 원본 파일명 목록
        List<String> types = dto.getFileTypes();              // MIME 타입 목록
        List<Long>   sizes = dto.getFileSizes();              // 파일 크기 목록
        if (keys != null) {
            List<DocumentAttachment> atts = new ArrayList<>();
            for (int i = 0; i < keys.size(); i++) {
                atts.add(DocumentAttachment.builder()
                        .draftDocument(savedDraftDocument)
                        .fileUrl(keys.get(i))
                        .fileName(names.get(i))
                        .fileType(types.get(i))
                        .fileSize(sizes.get(i))
                        .build());
                documentAttachmentRepository.saveAll(atts);
            }
        }

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
    public DraftDocument saveDraftAndLines(DraftCreateCommandDTO dto) {
        log.info("🟠 saveDraftAndLines() 호출됨");

        DraftDocument savedDraftDocument = draftRepository.save(dto.toEntity());
        Long docId = savedDraftDocument.getDocId();

        saveDocumentBoxEntry(dto.getEmployeeId(), docId, "기안자");

        List<ApprovalLineDTO> lines = dto.getApprovalLines();
        if (lines != null && !lines.isEmpty()) {
            approvalLineCommandService.saveManualLine(docId, lines, dto.getEmployeeId());
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
        // 8) 첨부파일 메타 저장
        List<String> keys  = dto.getAttachmentKeys();         // S3에서 발급된 키 목록
        List<String> names = dto.getOriginalFileNames();      // 원본 파일명 목록
        List<String> types = dto.getFileTypes();              // MIME 타입 목록
        List<Long>   sizes = dto.getFileSizes();              // 파일 크기 목록
        if (keys != null) {
            List<DocumentAttachment> atts = new ArrayList<>();
            for (int i = 0; i < keys.size(); i++) {
                atts.add(DocumentAttachment.builder()
                        .draftDocument(savedDraftDocument)
                        .fileUrl(keys.get(i))
                        .fileName(names.get(i))
                        .fileType(types.get(i))
                        .fileSize(sizes.get(i))
                        .isDeleted(false)
                        .build());
                documentAttachmentRepository.saveAll(atts);
            }
        }



        List<Long> approverIds = lines.stream()
                .map(ApprovalLineDTO::getEmployeeId)
                .toList();

        publisher.publishEvent(new NoticeEvent(
                this,
                "결재",
                "결재 요청",
                "결재 요청이 도착했습니다.",
                approverIds
        ));

        if (!cooperators.isEmpty()) {
            publisher.publishEvent(new NoticeEvent(
                    this, "결재",
                    "협조 요청", "협조 요청이 도착했습니다.",
                    cooperators
            ));
        }
        if (!receivers.isEmpty()) {
            publisher.publishEvent(new NoticeEvent(
                    this, "결재",
                    "문서 수신", "문서 수신 요청이 도착했습니다.",
                    receivers
            ));
        }
        if (!ccs.isEmpty()) {
            publisher.publishEvent(new NoticeEvent(
                    this, "결재",
                    "문서 참조", "문서 참조 요청이 도착했습니다.",
                    ccs
            ));
        }


        return savedDraftDocument; // ✅ Draft 반환
    }
}

