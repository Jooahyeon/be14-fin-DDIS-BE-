package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;

import java.util.List;

// 결재라인 실 매칭
public interface ApprovalLineAutoMatchService {
    /**
     * 기안자 ID를 기준으로 자동 결재라인을 생성한다.
     *
     * @param drafterId 기안자 사번
     * @return 결재라인 목록 (단계별 결재자 정보)
     */
    List<ApproverInfoQueryDTO> createApprovalLine(Long drafterId);

}

