package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.ApprovalLineDTO;
import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLine;
import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLineType;
import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalType;
import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
import com.ddis.ddis_hr.eapproval.command.domain.repository.ApprovalLineRepository;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DraftRepository;
import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ApproverQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.ApprovalLineQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


/**
 * 기안 상신 시 결재라인을 자동 생성·저장하는 Command 서비스
 */
@Service
@RequiredArgsConstructor
public class ApprovalLineCommandServiceImpl implements ApprovalLineCommandService {

    private final ApprovalLineQueryService approvalLineQueryService; // 미리보기 재활용
    private final ApprovalLineRepository approvalLineRepository;     // JPA
    private final DraftRepository draftRepository;                   // formId 조회용

    /**
     * @param docId       기안문서 PK
     * @param employeeId  기안자 사번
     * @return            첫 번째 approvalLineId (응답 DTO용)
     */
    @Transactional
    @Override
    public Long createAutoLine(Long docId, Long employeeId) {

        // 0) 기안문서 조회 → formId 확보
        Draft draft = draftRepository.findById(docId)
                .orElseThrow(() -> new IllegalArgumentException("Draft not found: " + docId));
        Long formId = draft.getFormId();

        // 1) 결재선 미리보기 조회 (단계별 결재자)
        List<ApproverQueryDTO> preview = approvalLineQueryService.generateApprovalLine(employeeId);

        // 2) DTO → 엔티티 변환
        List<ApprovalLine> entities = new ArrayList<>();
        for (ApproverQueryDTO pq : preview) {
            for (ApproverInfoQueryDTO info : pq.getApproverList()) {
                String status = info.getEmployeeId().equals(employeeId)
                        ? "기안"
                        : "대기중";


                entities.add(
                        ApprovalLine.builder()
                                .docId(docId)
                                .formId(formId)
                                .step(pq.getStepNo())
                                .employeeId(info.getEmployeeId())
                                .status(status)
                                .type(info.getLineType())               // ✅ 결재유형: 내부결재로 기본 설정
                                .lineType(ApprovalLineType.ACTUAL)         // ✅ 라인유형: 실제 결재라인
                                .build()
                );
            }
        }

        // 3) DB 저장
        List<ApprovalLine> saved = approvalLineRepository.saveAll(entities);

        // 4) 첫 행의 PK 반환
        return saved.isEmpty() ? 0L : saved.get(0).getApprovalLineId();
    }

    @Override
    public List<Long> saveManualLine(Long docId, List<ApprovalLineDTO> lines,Long form, Long drafterId) {
        Draft draft = draftRepository.findById(docId)
                .orElseThrow(() -> new IllegalArgumentException("Draft not found: " + docId));
        Long formId = draft.getFormId();

        List<ApprovalLine> entities = new ArrayList<>();
        for (ApprovalLineDTO dto : lines) {
            String status = dto.getEmployeeId().equals(drafterId) ? "기안" : "대기중";

            entities.add(ApprovalLine.builder()
                    .docId(docId)
                    .formId(formId)
                    .step(dto.getStep())
                    .employeeId(dto.getEmployeeId())
                    .status(status)
                    .type(dto.getType()) // 또는 dto.getType()으로 받으면 유연함
                    .lineType(ApprovalLineType.ACTUAL)
                    .build());
        }
        List<ApprovalLine> saved = approvalLineRepository.saveAll(entities);
        return saved.stream().map(ApprovalLine::getApprovalLineId).toList();
    }
}