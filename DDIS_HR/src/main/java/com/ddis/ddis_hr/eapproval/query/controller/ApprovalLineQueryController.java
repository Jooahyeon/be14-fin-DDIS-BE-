package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.ApprovalLineAutoMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ApprovalLineController
 *
 * GET /approval-line
 * 인증된 사용자 토큰(Authentication)에서 employeeId를 꺼내
 * ApprovalLineService.generateApprovalLine 호출 후 결과 반환
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/approval-line")
public class ApprovalLineQueryController {


    private final ApprovalLineAutoMatchService approvalLineAutoMatchService;

    /**
     * 기안자 ID로 자동 결재라인을 생성하여 반환
     *
     * @param employeeId 기안자 사번
     * @return 자동 생성된 결재자 목록
     */
    @GetMapping()
    public List<ApproverInfoQueryDTO> getAutoApprovalLine(@RequestParam("employeeId") Long employeeId) {
        return approvalLineAutoMatchService.createApprovalLine(employeeId);
    }
}





