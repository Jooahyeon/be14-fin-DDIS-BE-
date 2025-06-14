package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;

import java.util.List;

public interface ApprovalDocumentQueryService {

    // 결재 탭 (미결 + 심사중)
    List<DocumentDTO> getPendingApprovals(Long employeeId);

    // 진행 탭 (승인 + 심사중)
    List<DocumentDTO> getInProgressApprovals(Long employeeId);

    // 완료 탭 (승인 + 결재완료)
    List<DocumentDTO> getCompletedApprovals(Long employeeId);

    // 전체 탭 (모든 문서 + 반려 포함)
    List<DocumentDTO> getAllApprovals(Long employeeId);

}
