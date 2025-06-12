package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateResponseCommandDTO;
import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DraftRepository;

import com.ddis.ddis_hr.eapproval.query.service.ApprovalLineQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;


@RequiredArgsConstructor
@Service
@Slf4j
public class DraftCommandServiceImpl implements DraftCommandService {

    private final DraftRepository draftRepository;
    private final ApprovalLineCommandService approvalLineCommandService;

    /**
     * 기안문 생성 로직 / 결재라인 자동매칭
     */
    @Transactional
    @Override
    public DraftCreateResponseCommandDTO createDraft(DraftCreateCommandDTO dto) {

        // 1) 기안 저장 → docId 생성
        Draft saved = draftRepository.save(dto.toEntity());

        // 2) 결재라인 자동 생성 및 저장
        Long approvalLineId = approvalLineCommandService
                .createAutoLine(saved.getDocId(), dto.getEmployeeId());

        // 3) docId + approvalLineId(결재라인) 를 응답 DTO 로 반환
        return new DraftCreateResponseCommandDTO(saved.getDocId(), approvalLineId);
    }
//    /**
//     * 기안문 상세 조회 로직
//     */
//    @Override
//    @Transactional(readOnly = true)
//    public DraftDetailResponseQueryDTO getDraftDetail(Long docId) {
//        // 1) 엔티티 조회
//        Draft draft = draftRepository.findById(docId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 문서를 찾을 수 없습니다. docId=" + docId));
//
//        // 2) JSON → 객체 변환 (예: EditorData)
//        EditorData editorData = null;
//        try {
//            editorData = objectMapper.readValue(draft.getDocContent(), EditorData.class);
//        } catch (Exception e) {
//            throw new RuntimeException("문서 내용을 파싱하는 데 실패했습니다.", e);
//        }
//
//        // 3) 부가정보 조회 (부서·팀·직책·작성자 이름 등)
//        String departmentName = departmentService.getNameById(editorData.getDepartmentId());
//        String teamName       = teamService.getNameById(editorData.getTeamId());
//        String positionName   = positionService.getNameById(editorData.getPositionId());
//        String drafterName    = userService.getNameById(draft.getEmployeeId());
//
//        // 4) 화면용 DTO 조립
//        return DraftDetailResponseQueryDTO.builder()
//                .docId(draft.getDocId())
//                .docTitle(draft.getDocTitle())
//                .editorData(editorData)
//                .preservePeriod(draft.getPreservePeriod())
//                .expirationDate(draft.getExpirationDate())
//                .docStatus(draft.getDocStatus())
//                .createdAt(draft.getCreatedAt())
//                .submittedAt(draft.getSubmittedAt())
//                .draftVersion(draft.getDraftVersion())
//                .formId(draft.getFormId())
//                .employeeId(draft.getEmployeeId())
//                .departmentName(departmentName)
//                .teamName(teamName)
//                .positionName(positionName)
//                .drafterName(drafterName)
//                .build();
//    }
}
