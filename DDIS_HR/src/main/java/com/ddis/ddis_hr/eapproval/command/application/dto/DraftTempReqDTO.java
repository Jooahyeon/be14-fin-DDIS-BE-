package com.ddis.ddis_hr.eapproval.command.application.dto;

import lombok.Getter;
import lombok.Setter;

// 임시저장 DTO

@Getter
@Setter
public class DraftTempReqDTO {

    private Long docId;
    private Long employeeId;
    private Long formId;
    private String title;
    private String docContent;
    private Integer preservePeriod;

    /* ---------- JSON 직렬화 대상 ---------- */
    private Object approvalLines;   // front → List<Map<String, Object>>
    private Object receivers;       // 수신자
    private Object ccs;             // 참조자
    private Object uploadedFiles;   // 첨부파일 메타정보
}
