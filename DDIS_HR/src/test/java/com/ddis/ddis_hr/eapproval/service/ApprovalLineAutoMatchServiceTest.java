package com.ddis.ddis_hr.eapproval.service;


import com.ddis.ddis_hr.eapproval.query.service.ApprovalLineAutoMatchServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
class ApprovalLineAutoMatchServiceTest {

    @Autowired
    ApprovalLineAutoMatchServiceImpl service;

    @Test
    void createApprovalLine_logsEntry(CapturedOutput output) {
        service.createApprovalLine(20151123010001L);
        assertTrue(output.getOut().contains("▶▶ createApprovalLine() 진입: drafterId="));
    }
}



//
//import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
//import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
//import com.ddis.ddis_hr.eapproval.query.mapper.ApproverMapper;
//import com.ddis.ddis_hr.eapproval.query.mapper.FindDrafterMapper;
//import com.ddis.ddis_hr.eapproval.query.service.ApprovalLineAutoMatchServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ApprovalLineAutoMatchServiceTest {
//
//    @InjectMocks
//    private ApprovalLineAutoMatchServiceImpl approvalLineAutoMatchService;
//
//    @Mock
//    private FindDrafterMapper findDrafterMapper;
//
//    @Mock
//    private ApproverMapper approverMapper;
//
//    @Test
//    void 자동결재라인_생성_성공() {
//        // Given
//        Long drafterId = 100L;
//        FindDrafterQueryDTO drafter = new FindDrafterQueryDTO();
//        drafter.setRankOrder(1); // 예: 대리
//        drafter.setTeamId(10L);
//        drafter.setDepartmentId(20L);
//        drafter.setHeadId(30L);
//
//        when(findDrafterMapper.findDrafterInfo(drafterId)).thenReturn(drafter);
//
//        // 1차 결재자: rankOrder=2, orgType=team, orgId=10
//        ApproverInfoQueryDTO firstApprover = new ApproverInfoQueryDTO();
//        firstApprover.setEmployeeId(200L);
//        firstApprover.setEmployeeName("과장A");
//
//        when(approverMapper.findApproverByRankOrderAndOrg(2, "team", 10L))
//                .thenReturn(firstApprover);
//
//        // 2차 결재자 탐색: rankOrder=3
//        // └ 팀 조회 시 없으면
//        when(approverMapper.findApproverByRankOrderAndOrg(3, "team", 10L))
//                .thenReturn(null);
//        // └ 부서 조회 시 없으면
//        when(approverMapper.findApproverByRankOrderAndOrg(3, "department", 20L))
//                .thenReturn(null);
//        // └ 본부 조회 시 없으면
//        when(approverMapper.findApproverByRankOrderAndOrg(3, "head", 30L))
//                .thenReturn(null);
//
//        // 3차 결재자 탐색: rankOrder=4
//        when(approverMapper.findApproverByRankOrderAndOrg(4, "team", 10L))
//                .thenReturn(null);
//        when(approverMapper.findApproverByRankOrderAndOrg(4, "department", 20L))
//                .thenReturn(null);
//        when(approverMapper.findApproverByRankOrderAndOrg(4, "head", 30L))
//                .thenReturn(null);
//
//        // When
//        List<ApproverInfoQueryDTO> result = approvalLineAutoMatchService.createApprovalLine(drafterId);
//
//        // Then
//        assertEquals(1, result.size());
//        assertEquals("과장A", result.get(0).getEmployeeName());
//        assertEquals("1차 결재", result.get(0).getTypeLabel());
//    }
//}
//
