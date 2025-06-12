package com.ddis.ddis_hr.eapproval.command.application.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalLineDTO {


        private Long approvalLineId;
        private Long docId;
        private int step;          // 1,2,3…
        private Long employeeId;   // 결재자
        private Long positionId;
        private String status;     // 대기중·승인·반려
        private String type;

}
