package com.ddis.ddis_hr.eapproval.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 저장 요청 DTO
@Getter
@Setter
@NoArgsConstructor
public class ApprovalLineRequestDTO {
    private Long docId;
    private int step;
    private String status;
    private String type;
    private String lineType;
    private String opinion;
    private Long formId;
    private Long positionId;
    private Long employeeId;
}
