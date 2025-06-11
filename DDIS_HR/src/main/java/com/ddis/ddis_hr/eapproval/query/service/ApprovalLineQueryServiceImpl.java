package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLineType;
import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalType;
import com.ddis.ddis_hr.eapproval.query.dto.ApprovalStepQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ApproverQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.ApprovalStepMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.ApproverMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.FindDrafterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApprovalLineQueryServiceImpl implements ApprovalLineQueryService {

    private final FindDrafterMapper drafterMapper;
    private final ApprovalStepMapper stepMapper;
    private final ApproverMapper approverMapper;

    @Autowired
    public ApprovalLineQueryServiceImpl(
            FindDrafterMapper drafterMapper,
            ApprovalStepMapper stepMapper,
            ApproverMapper approverMapper
    ) {
        this.drafterMapper = drafterMapper;
        this.stepMapper = stepMapper;
        this.approverMapper = approverMapper;
    }

    @Override
    public List<ApproverQueryDTO> generateApprovalLine(Long employeeId) {
        System.out.println("▶ [자동결재선] 호출됨 - employeeId: " + employeeId);

        FindDrafterQueryDTO drafter = drafterMapper.findDrafterInfo(employeeId);
        if (drafter == null) {
            System.out.println("❌ 기안자 정보 조회 실패! employeeId = " + employeeId);
            return new ArrayList<>();
        }

        System.out.println("✅ 기안자 정보: "
                + "teamId=" + drafter.getTeamId()
                + ", departmentId=" + drafter.getDepartmentId()
                + ", headId=" + drafter.getHeadId()
                + ", positionId=" + drafter.getPositionId());

        List<ApprovalStepQueryDTO> steps =
                stepMapper.findApprovalStepsByBasePositionId(drafter.getPositionId());

        System.out.println("📌 결재단계 수: " + steps.size());

        List<ApproverQueryDTO> result = new ArrayList<>();

        for (ApprovalStepQueryDTO stepDto : steps) {
            int stepNo = stepDto.getStep();
            String positionName = stepDto.getPositionName();

            String orgType;
            Long orgId;
            switch (stepNo) {
                case 1 -> {
                    orgType = "team";
                    orgId = drafter.getTeamId();
                }
                case 2 -> {
                    orgType = "department";
                    orgId = drafter.getDepartmentId();
                }
                default -> {
                    orgType = "head";
                    orgId = drafter.getHeadId();
                }
            }
            System.out.println("🔄 Step " + stepNo + ": 직책=" + positionName +
                    ", 조직타입=" + orgType + ", orgId=" + orgId);

            ApproverInfoQueryDTO approver = approverMapper
                    .findApproverByPositionAndOrg(positionName, orgType, orgId);

            List<ApproverInfoQueryDTO> approverList = new ArrayList<>();

            if (approver != null) {
                System.out.println("✅ 결재자 찾음: " + approver.getEmployeeName() + " (" + approver.getEmployeeId() + ")");

                // ✅ 영어 코드값 세팅
                approver.setType(ApprovalType.INTERNAL.name()); // INTERNAL
                approver.setLineType(ApprovalLineType.ACTUAL.name()); // ACTUAL

                // ✅ 한글 라벨 세팅
                approver.setTypeLabel("내부결재");
                approver.setLineTypeLabel("실제 결재선");

                approverList.add(approver);
            } else {
                System.out.println("⚠️ 결재자 없음 → step=" + stepNo +
                        ", position=" + positionName + ", orgType=" + orgType + ", orgId=" + orgId);
            }

            result.add(new ApproverQueryDTO(stepNo, approverList));
        }

        System.out.println("🎯 최종 결재선 단계 수: " + result.size());
        return result;
    }
}