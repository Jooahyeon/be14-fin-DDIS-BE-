package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.ApproverMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.FindDrafterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApprovalLineAutoMatchServiceImpl implements ApprovalLineAutoMatchService {

    // 기안자 제외 후 최대 몇 단계까지 자동 결재선을 생성할지 정의 (여기서는 3단계)
    private static final int MAX_APPROVAL_STEP = 3;

    private final FindDrafterMapper   findDrafterMapper;  // 기안자 정보 조회 매퍼
    private final ApproverMapper      approver;           // 결재자 조회 매퍼


    @Override
    public List<ApproverInfoQueryDTO> createApprovalLine(Long drafterId) {

        log.info("▶▶ createApprovalLine() 진입: drafterId={}", drafterId);

        // 1) 기안자 정보 조회
        //   - drafterId(사번)를 이용해 팀·부서·본부 ID, 직급 순서 등 포함된 DTO 반환
        FindDrafterQueryDTO drafter = findDrafterMapper.findDrafterInfo(drafterId);
        log.debug("▶▶ drafter DTO = {}", drafter);


        // 2) 결과 리스트 초기화
        List<ApproverInfoQueryDTO> result = new ArrayList<>();

        // ——— 3) 1단계: 기안자 자신을 무조건 첫 번째 결재자로 추가 ———
        ApproverInfoQueryDTO drafterStep = new ApproverInfoQueryDTO();
        drafterStep.setStep(1);                            // 단계 번호 = 1
        drafterStep.setEmployeeId(drafter.getEmpId());     // 기안자 사번
        drafterStep.setEmployeeName(drafter.getName());    // 기안자 이름
        drafterStep.setTeamName(drafter.getTeamName());    // 소속 팀명
        drafterStep.setDepartmentName(drafter.getDepartmentName()); // 소속 부서명
        drafterStep.setRankId(drafter.getRankId());        // 직급 ID
        drafterStep.setRankName(drafter.getRankName());    // 직급명
        drafterStep.setRankOrder(drafter.getRankOrder());  // 직급 정렬 순서
        drafterStep.setType("기안");                        // 결재 유형 코드
        drafterStep.setLineType("ACTUAL");                  // 결재선 유형 코드
        drafterStep.setTypeLabel("기안");                   // 한글 라벨
//        drafterStep.setLineTypeLabel("실제 결재선");          // 한글 라벨
        result.add(drafterStep);                           // 리스트에 추가

        // ——— 4) 2~4단계: 상위 직급자 자동 매칭 ———
        int baseOrder = drafter.getRankOrder();            // 기준 직급 순서

        // 기안자 제외 후 i = 1..MAX_APPROVAL_STEP 만큼 반복
        for (int i = 1; i <= MAX_APPROVAL_STEP; i++) {
            int stepNo     = i + 1;            // 결재 단계: 2,3,4
            int targetRank = baseOrder - i;    // “위급” 검색

            // 더 이상 상위 직급이 없으면 루프 종료
            if (targetRank <= 0) {
                break;
            }

            ApproverInfoQueryDTO approverInfo = null;

            // 4-1) 같은 팀(team)에서 targetRank인 직원 조회
            approverInfo = approver.findApproverByRankOrderAndOrg(
                    targetRank,
                    "team",
                    drafter.getTeamId(),
                    stepNo,
                    "결재",
                    "ACTUAL"
            );

            log.debug("  - 팀 조회 결과: {}", approverInfo);


            // 4-2) 팀에 없다면 같은 부서(department)에서 조회
            if (approverInfo == null) {
                approverInfo = approver.findApproverByRankOrderAndOrg(
                        targetRank,
                        "department",
                        drafter.getDepartmentId(),
                        stepNo,
                        "결재",
                        "ACTUAL"
                );
            }
            log.debug("  - 부서 조회 결과: {}", approverInfo);

            // 4-3) 부서에도 없다면 본부(head)에서 조회
            if (approverInfo == null) {
                approverInfo = approver.findApproverByRankOrderAndOrg(
                        targetRank,
                        "head",
                        drafter.getHeadId(),
                        stepNo,
                        "결재",
                        "ACTUAL"
                );
            }

            // 4-4) 조회된 결재자가 있으면 리스트에 추가
            if (approverInfo != null) {
                result.add(approverInfo);
            }
            // approverInfo == null 이면 해당 단계는 스킵
        }

        // 5) 완성된 결재선 리스트 반환
        return result;

    }


}