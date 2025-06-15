//package com.ddis.ddis_hr.eapproval.query.service;
//
//import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLineType;
//import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalType;
//import com.ddis.ddis_hr.eapproval.query.dto.ApprovalStepQueryDTO;
//import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
//import com.ddis.ddis_hr.eapproval.query.dto.ApproverQueryDTO;
//import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
//import com.ddis.ddis_hr.eapproval.query.mapper.ApprovalStepMapper;
//import com.ddis.ddis_hr.eapproval.query.mapper.ApproverMapper;
//import com.ddis.ddis_hr.eapproval.query.mapper.FindDrafterMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ApprovalLineQueryServiceImpl implements ApprovalLineQueryService {
//
//    private final FindDrafterMapper drafterMapper;
//    private final ApprovalStepMapper stepMapper;
//    private final ApproverMapper approverMapper;
//
//    public ApprovalLineQueryServiceImpl(
//            FindDrafterMapper drafterMapper,
//            ApprovalStepMapper stepMapper,
//            ApproverMapper approverMapper
//    ) {
//        this.drafterMapper   = drafterMapper;
//        this.stepMapper      = stepMapper;
//        this.approverMapper  = approverMapper;
//    }
//
//    @Override
//    public List<ApproverQueryDTO> generateApprovalLine(Long employeeId) {
//        System.out.println("▶ [자동결재선] 호출됨 - employeeId: " + employeeId);
//
//        // 1) 기안자 정보 조회
//        FindDrafterQueryDTO drafter = drafterMapper.findDrafterInfo(employeeId);
//        if (drafter == null) {
//            System.out.println("❌ 기안자 정보 조회 실패! employeeId = " + employeeId);
//            return new ArrayList<>();
//        }
//
//        System.out.println("✅ 기안자 정보: "
//                + "teamId=" + drafter.getTeam()
//                + ", departmentId=" + drafter.getDepartmentId()
//                + ", headId=" + drafter.getHeadId()
//                + ", positionId=" + drafter.getPositionId());
//
//        List<ApproverQueryDTO> result = new ArrayList<>();
//
//        // ——————————————————————————————————————————————
//        // STEP 1: “기안자”를 첫 번째 단계로 추가
//        List<ApproverInfoQueryDTO> drafterList = new ArrayList<>();
//        ApproverInfoQueryDTO drafterInfo = new ApproverInfoQueryDTO();
//        drafterInfo.setEmployeeId(drafter.getEmpId());
//        drafterInfo.setEmployeeName(drafter.getName());
//        drafterInfo.setPositionName(drafter.getPosition());
//        drafterInfo.setTeamName(drafter.getTeam());
//        drafterInfo.setDepartmentName(drafter.getDepartment());
//        drafterInfo.setType(ApprovalType.INTERNAL.name());
//        drafterInfo.setLineType(ApprovalLineType.ACTUAL.name());
//        // 라벨은 자유롭게 조정하세요
//        drafterInfo.setTypeLabel("기안자");
//        drafterInfo.setLineTypeLabel("기안자");
//        drafterList.add(drafterInfo);
//        result.add(new ApproverQueryDTO(1, drafterList));
//
//        // 2) DB 에 정의된 실제 결재 단계 가져오기
//        List<ApprovalStepQueryDTO> steps =
//                stepMapper.findApprovalStepsByBasePositionId(drafter.getPositionId());
//        System.out.println("📌 DB 결재단계 수: " + steps.size());
//
//        // 각 단계 번호를 +1 해서 “2부터” 배치
//        for (ApprovalStepQueryDTO stepDto : steps) {
//            int stepNo = stepDto.getStep() + 1;
//            String positionName = stepDto.getPositionName();
//
//            // orgType/orgId 매핑: case 2=팀, 3=부서, 그 외=본부장
//            String orgType;
//            Long orgId;
//            switch (stepNo) {
//                case 2 -> {
//                    orgType = "team";
//                    orgId   = drafter.getTeamId();
//                }
//                case 3 -> {
//                    orgType = "department";
//                    orgId   = drafter.getDepartmentId();
//                }
//                default -> {
//                    orgType = "head";
//                    orgId   = drafter.getHeadId();
//                }
//            }
//
//            System.out.println("🔄 Step " + stepNo
//                    + ": 직책=" + positionName
//                    + ", 조직타입=" + orgType
//                    + ", orgId=" + orgId);
//
//            ApproverInfoQueryDTO approver = approverMapper
//                    .findApproverByPositionAndOrg(positionName, orgType, orgId);
//
//            List<ApproverInfoQueryDTO> approverList = new ArrayList<>();
//            if (approver != null) {
//                System.out.println("✅ 결재자 찾음: "
//                        + approver.getEmployeeName()
//                        + " (" + approver.getEmployeeId() + ")");
//
//                approver.setType(ApprovalType.INTERNAL.name());
//                approver.setLineType(ApprovalLineType.ACTUAL.name());
//                approver.setTypeLabel("내부결재");
//                approver.setLineTypeLabel("실제 결재선");
//                approverList.add(approver);
//            } else {
//                System.out.println("⚠️ 결재자 없음 → step="
//                        + stepNo + ", position=" + positionName
//                        + ", orgType=" + orgType + ", orgId=" + orgId);
//            }
//
//            result.add(new ApproverQueryDTO(stepNo, approverList));
//        }
//
//        System.out.println("🎯 최종 결재선 단계 수: " + result.size());
//        return result;
//    }
//}
