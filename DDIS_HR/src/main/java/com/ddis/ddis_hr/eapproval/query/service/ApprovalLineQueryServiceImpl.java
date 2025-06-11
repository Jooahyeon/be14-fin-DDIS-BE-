package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ApprovalStepQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ApproverQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.ApprovalStepMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.ApproverMapper;
import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.FindDrafterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ApprovalLineServiceImpl
 *
 * ApprovalLineService 구현체.
 * 1) FindDrafterMapper로 기안자 조직정보 조회
 * 2) ApprovalStepMapper로 단계별 직책 목록 조회
 * 3) ApproverMapper로 조직+직책 기준 실제 결재자 조회
 * 4) ApproverDTO로 조립하여 반환
 */
@Service
public class ApprovalLineQueryServiceImpl implements ApprovalLineQueryService {

    private final FindDrafterMapper drafterMapper; // 토큰→기안자 정보 조회용 매퍼
    private final ApprovalStepMapper stepMapper;   // 단계별 직책 목록 조회용 매퍼
    private final ApproverMapper approverMapper;   // 조직 기준 결재자 조회용 매퍼

    @Autowired
    public ApprovalLineQueryServiceImpl(
            FindDrafterMapper drafterMapper,
            ApprovalStepMapper stepMapper,
            ApproverMapper approverMapper
    ) {
        this.drafterMapper   = drafterMapper;
        this.stepMapper      = stepMapper;
        this.approverMapper  = approverMapper;
    }

    /**
     * generateApprovalLine
     *
     * 1) 기안자(employeeId) 정보 조회 → FindDrafterDTO
     * 2) positionId 기준 ApprovalStepDTO 리스트 조회
     * 3) 각 단계별로
     *    - stepNo: 결재 순서
     *    - positionName: 해당 단계 요구 직책명
     *    - 조직 단위(team/department/head)와 ID 결정
     *    - ApproverMapper 호출하여 EmployeeSimpleDTO 조회
     *    - ApproverDTO에 담아 리스트에 추가
     * 4) 완성된 ApproverDTO 리스트 반환
     *
     * @param employeeId 로그인한 사용자의 사번
     * @return 단계별 결재자 정보 리스트
     */
    @Override
    public List<ApproverQueryDTO> generateApprovalLine(Long employeeId) {
        // 1) DrafterInfoDTO로부터 기안자 조직정보 획득
        FindDrafterQueryDTO drafter = drafterMapper.findDrafterInfo(employeeId);

        // 2) drafter.positionId 기준 단계별 직책 목록 조회
        List<ApprovalStepQueryDTO> steps = stepMapper
                .findApprovalStepsByBasePositionId(drafter.getPositionId());

        List<ApproverQueryDTO> result = new ArrayList<>();

        // 3) 단계별 실제 결재자 매칭
        for (ApprovalStepQueryDTO stepDto : steps) {
            int stepNo            = stepDto.getStep();          // 결재 단계 순번
            String positionName   = stepDto.getPositionName();  // 요구 직책명

            // 단계별 조직 구분 및 ID 설정
            String orgType;
            Long   orgId;
            switch (stepNo) {
                case 1:
                    orgType = "team";        // 1단계: 팀 단위 결재자
                    orgId   = drafter.getTeamId();
                    break;
                case 2:
                    orgType = "department";  // 2단계: 부서 단위 결재자
                    orgId   = drafter.getDepartmentId();
                    break;
                default:
                    orgType = "head";        // 3단계 이상: 본부 단위 결재자
                    orgId   = drafter.getHeadId();
                    break;
            }

            // 3-1) 조직+직책 기준 결재자 조회
            ApproverInfoQueryDTO approver = approverMapper
                    .findApproverByPositionAndOrg(positionName, orgType, orgId);

            // 3-2) 조회 결과 null 체크 후 리스트로 래핑
            List<ApproverInfoQueryDTO> approverList = new ArrayList<>();
            if (approver != null) {
                approverList.add(approver);
            }

            // 3-3) stepNo + approverList → ApproverDTO 생성
            result.add(new ApproverQueryDTO(stepNo, approverList));
        }

        return result;
    }
}
