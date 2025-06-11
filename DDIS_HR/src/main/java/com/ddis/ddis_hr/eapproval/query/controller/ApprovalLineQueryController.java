package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.ApprovalLineQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ApprovalLineController
 *
 * GET /approval-line
 * 인증된 사용자 토큰(Authentication)에서 employeeId를 꺼내
 * ApprovalLineService.generateApprovalLine 호출 후 결과 반환
 */
@RestController
@RequestMapping("/approval-line")
public class ApprovalLineQueryController {

    private final ApprovalLineQueryService approvalLineQueryService;

    @Autowired
    public ApprovalLineQueryController(ApprovalLineQueryService approvalLineQueryService) {
        this.approvalLineQueryService = approvalLineQueryService;
    }

    /**
     * getApprovalLine
     *
     * @param authentication Spring Security 인증 정보
     *                       - getName() 으로 employeeId 획득
     * @return 200 OK + 단계별 결재자 정보(ApproverDTO 리스트)
     */
    @GetMapping
    public ResponseEntity<List<ApproverQueryDTO>> getApprovalLine(Authentication authentication) {
        // 인증 컨텍스트에서 사용자 사번(subject) 획득
        Long employeeId = Long.valueOf(authentication.getName());

        // 서비스 호출: 결재라인 생성
        List<ApproverQueryDTO> line = approvalLineQueryService.generateApprovalLine(employeeId);

        // HTTP 200 응답으로 DTO 리스트 반환
        return ResponseEntity.ok(line);
    }
}


