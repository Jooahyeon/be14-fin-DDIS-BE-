package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.ApprovalLineDTO;
import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLine;
import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLineType;
import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
import com.ddis.ddis_hr.eapproval.command.domain.repository.ApprovalLineRepository;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DraftRepository;
import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.ApprovalLineAutoMatchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 기안 상신 시 결재라인을 자동 생성·저장하는 Command 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ApprovalLineCommandServiceImpl implements ApprovalLineCommandService {

    private final ApprovalLineAutoMatchService approvalLineAutoMatchService;
    private final ApprovalLineRepository approvalLineRepository;
    private final DraftRepository draftRepository;

    /**
     * @param docId      기안문서 PK
     * @param employeeId 기안자 사번
     * @return 첫 번째 approvalLineId (응답 DTO용)
     */
    @Transactional
    @Override
    public Long createAutoLine(Long docId, Long employeeId) {

        // 0) 기안문서 조회 → formId 확보
        Draft draft = draftRepository.findById(docId)
                .orElseThrow(() -> new IllegalArgumentException("Draft not found: " + docId));
        Long formId = draft.getFormId();

        // 1) 결재선 미리보기 조회 (단계별 결재자)
        List<ApproverInfoQueryDTO> preview = approvalLineAutoMatchService.createApprovalLine(employeeId);

        // 2) DTO → 엔티티 변환
        List<ApprovalLine> entities = new ArrayList<>();
        for (ApproverInfoQueryDTO info : preview) {
            int step = info.getStep();
            String status = info.getEmployeeId().equals(employeeId) ? "기안" : "대기중";
            entities.add(
                    ApprovalLine.builder()
                            .docId(docId)
                            .formId(formId)
                            .step(step)
                            .employeeId(info.getEmployeeId())
                            .status(status)
                            .type(info.getType())
                            .lineType(ApprovalLineType.ACTUAL)
                            .build()
            );
        }

        // 3) DB 저장
        List<ApprovalLine> saved = approvalLineRepository.saveAll(entities);
        return saved.isEmpty() ? 0L : saved.get(0).getApprovalLineId();
    }

    /**
     * 수동 지정된 결재라인 저장
     * @param docId      기안문서 PK
     * @param lines      저장할 결재라인 DTO 리스트
     * @param drafterId  기안자 사번
     * @return 저장된 ApprovalLine ID 리스트
     */
    @Override
    public List<Long> saveManualLine(Long docId, List<ApprovalLineDTO> lines, Long drafterId) {
        // 0) 기안문서 조회 → formId 확보
        Draft draft = draftRepository.findById(docId)
                .orElseThrow(() -> new IllegalArgumentException("Draft not found: " + docId));
        Long formId = draft.getFormId();

        // 1) DTO → 엔티티 변환
        List<ApprovalLine> entities = new ArrayList<>();
        for (ApprovalLineDTO dto : lines) {
            int step = dto.getStep();
            String status = dto.getEmployeeId().equals(drafterId) ? "기안" : "대기중";
            log.info("⛳ 수동 결재라인 저장 → step: {}, type: {}, empId: {}, docId: {}",
                    step, dto.getType(), dto.getEmployeeId(), docId);

            entities.add(ApprovalLine.builder()
                    .docId(docId)
                    .formId(formId)
                    .step(step)
                    .employeeId(dto.getEmployeeId())
                    .status(status)
                    .type(dto.getType())
                    .lineType(ApprovalLineType.ACTUAL)
                    .build());
        }

        // 2) DB 저장 및 ID 리스트 반환
        return approvalLineRepository.saveAll(entities).stream()
                .map(ApprovalLine::getApprovalLineId)
                .toList();
    }
}






