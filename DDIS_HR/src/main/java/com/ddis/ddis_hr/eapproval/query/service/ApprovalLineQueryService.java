package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverQueryDTO;

import java.util.List;


/**
 * ApprovalLineService
 *
 * 자동 생성된 결재라인을 제공하는 서비스 인터페이스.
 * 구현체에서는 기안자 정보 조회, 단계별 직책 조회, 조직 기준 결재자 매칭 로직을 담당.
 */
public interface ApprovalLineQueryService {

    /**
     * generateApprovalLine
     *
     * @param employeeId 로그인한 사용자의 사번 (또는 토큰 subject)
     * @return 단계별 실제 결재자 정보를 담은 ApproverDTO 리스트
     */
    List<ApproverQueryDTO> generateApprovalLine(Long employeeId);
}
