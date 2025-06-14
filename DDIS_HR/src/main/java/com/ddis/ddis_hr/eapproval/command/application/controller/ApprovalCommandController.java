package com.ddis.ddis_hr.eapproval.command.application.controller;

import com.ddis.ddis_hr.eapproval.command.application.dto.ApprovalActionRequestDTO;
import com.ddis.ddis_hr.eapproval.command.application.service.ApprovalWorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 승인/반려 커맨드 컨트롤러
 * - 결재 워크플로우 서비스(ApprovalWorkflowService)를 통해
 *   승인(approve) 및 반려(reject) 액션을 처리합니다.
 */
@Slf4j
@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalCommandController {

    private final ApprovalWorkflowService approvalWorkflowService;

    /**
     * [POST] /api/approvals/{lineId}/approve
     * 현재 로그인된 사용자가 지정된 결재라인(lineId)을 승인합니다.
     *
     * @param lineId         PathVariable: 처리할 ApprovalLine의 고유 ID
     * @param req            RequestBody: 클라이언트가 전달한 ApprovalActionRequestDTO (의견 포함)
     * @param authentication Spring Security 인증 정보 (JWT 토큰에서 파싱된 Principal)
     * @return               HTTP 200 OK (성공 시)
     */
    @PostMapping("/{lineId}/approve")
    public ResponseEntity<Void> approve(
            @PathVariable Long lineId,
            @RequestBody ApprovalActionRequestDTO req,
            Authentication authentication
    ) {
        // 1) Authentication 객체에서 사원번호(employeeId) 추출
        //    기본적으로 JWT의 sub(subject)가 authentication.getName()으로 매핑됨
        Long employeeId = Long.parseLong(authentication.getName());

        log.debug("approve 호출: lineId={}, employeeId={}, opinion={}",
                lineId, employeeId, req.getOpinion());

        // 2) 승인 로직 실행
        approvalWorkflowService.approveLine(lineId, employeeId, req.getOpinion());

        // 3) 처리 완료 응답 반환
        return ResponseEntity.ok().build();
    }

    /**
     * [POST] /api/approvals/{lineId}/reject
     * 현재 로그인된 사용자가 지정된 결재라인(lineId)을 반려합니다.
     *
     * @param lineId         PathVariable: 처리할 ApprovalLine의 고유 ID
     * @param req            RequestBody: 클라이언트가 전달한 ApprovalActionRequestDTO (의견 포함)
     * @param authentication Spring Security 인증 정보 (JWT 토큰에서 파싱된 Principal)
     * @return               HTTP 200 OK (성공 시)
     */
    @PostMapping("/{lineId}/reject")
    public ResponseEntity<Void> reject(
            @PathVariable Long lineId,
            @RequestBody ApprovalActionRequestDTO req,
            Authentication authentication
    ) {
        Long employeeId = Long.parseLong(authentication.getName());

        log.debug("reject 호출: lineId={}, employeeId={}, opinion={}",
                lineId, employeeId, req.getOpinion());

        approvalWorkflowService.rejectLine(lineId, employeeId, req.getOpinion());
        return ResponseEntity.ok().build();
    }
}
