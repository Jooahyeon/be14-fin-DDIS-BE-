package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.ApprovalLineAutoMatchService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

    /**
     * getApprovalLine
     *
     * @param authentication Spring Security 인증 정보
     *                       - getName() 으로 employeeId 획득
     * @return 200 OK + 단계별 결재자 정보(ApproverDTO 리스트)
     */
//    @GetMapping
//    public ResponseEntity<List<ApproverQueryDTO>> getApprovalLine(Authentication authentication) {
//        // 인증 컨텍스트에서 사용자 사번(subject) 획득
//        Long employeeId = Long.valueOf(authentication.getName());
//
//        // 서비스 호출: 결재라인 생성
//        List<ApproverQueryDTO> line = approvalLineQueryService.generateApprovalLine(employeeId);
//
//        // HTTP 200 응답으로 DTO 리스트 반환
//        return ResponseEntity.ok(line);
//    }





