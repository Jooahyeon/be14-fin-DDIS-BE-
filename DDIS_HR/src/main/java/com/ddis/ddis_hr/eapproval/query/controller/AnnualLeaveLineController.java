package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.AnnualLeaveStrategy;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 연차신청서 결재선 컨트롤러
@AllArgsConstructor
@RestController
@RequestMapping("/approval-line/leave")
public class AnnualLeaveLineController {

    private final AnnualLeaveStrategy strategy;

    @GetMapping
    public List<ApproverInfoQueryDTO> getAnnualLeaveLine(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        return strategy.createApprovalLine(employeeId);
    }
}
