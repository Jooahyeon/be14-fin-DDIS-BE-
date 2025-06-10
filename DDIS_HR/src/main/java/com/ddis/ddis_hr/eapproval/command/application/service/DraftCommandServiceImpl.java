package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DraftRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
@Slf4j
public class DraftCommandServiceImpl implements DraftCommandService {

    private final DraftRepository draftRepository;
//    private final DepartmentService departmentService;
//    private final TeamService teamService;
//    private final PositionService positionService;
//    private final UserService userService;
//    private final ObjectMapper objectMapper; // JSON 파싱용

    /**
     * 기안문 생성 로직
     */
    @Override
    @Transactional
    public Long createDraft(DraftCreateCommandDTO commandDto) {
        // 1) DTO → 엔티티 변환 (doc_content: JSON 문자열)
        Draft draft = commandDto.toEntity();

        // 2) 저장
        Draft saved = draftRepository.save(draft);

        System.out.println("✅ 저장 성공: docId = " + saved.getDocId());


        // 3) 생성된 문서 ID 반환
        return saved.getDocId();
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
