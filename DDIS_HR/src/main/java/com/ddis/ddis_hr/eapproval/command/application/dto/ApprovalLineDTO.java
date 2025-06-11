package com.ddis.ddis_hr.eapproval.command.application.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "approval_line")
public class ApprovalLineDTO {

        @Id
        @GeneratedValue
        private Long approvalLineId;

        @Column(nullable = false)
        private Long docId;

        @Column(nullable = false)
        private int step;          // 1,2,3…

        @Column(nullable = false)
        private Long employeeId;   // 결재자

        @Column(nullable = false, length = 20)
        private String status;     // 대기중·승인·반려

}
