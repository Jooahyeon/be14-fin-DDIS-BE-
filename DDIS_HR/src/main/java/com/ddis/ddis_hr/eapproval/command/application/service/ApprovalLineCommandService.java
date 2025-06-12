package com.ddis.ddis_hr.eapproval.command.application.service;

public interface ApprovalLineCommandService {
    /**
     * 기안(docId)에 결재라인을 자동 생성하여 저장하고
     * 첫 번째 approvalLineId를 반환한다.
     */
    Long createAutoLine(Long docId, Long employeeId);


}
