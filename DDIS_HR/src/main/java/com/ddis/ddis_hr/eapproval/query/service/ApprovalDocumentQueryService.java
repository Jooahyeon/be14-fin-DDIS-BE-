package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;

import java.util.List;

public interface ApprovalDocumentQueryService {

    // 상태별 기안문서 목록 조회
//    List<DraftDTO> getDraftsByStatus(Long employeeId, String status);

    // 기안함 전체탭
    List<DraftDTO> getMyDrafts(Long employeeId);


    // 결재함
    List<DocumentDTO> getPendingApprovals(Long employeeId);
    List<DocumentDTO> getInProgressApprovals(Long employeeId);
    List<DocumentDTO> getCompletedApprovals(Long employeeId);
    List<DocumentDTO> getAllApprovals(Long employeeId);

}
