package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.ApprovalLineDTO;

import java.util.List;

// 결재라인 자동 매칭 로직 서비스
public interface ApprovalLineCommandService {
    /**
     * 기안(docId)에 결재라인을 자동 생성하여 저장하고
     * 첫 번째 approvalLineId를 반환한다.
     */
    Long createAutoLine(Long docId, Long employeeId);


//    List<Long> saveManualLine(Long docId, List<ApprovalLineDTO> lines,Long formId, Long drafterId);

    List<Long> saveManualLine(Long docId, List<ApprovalLineDTO> lines, Long drafterId);
}
