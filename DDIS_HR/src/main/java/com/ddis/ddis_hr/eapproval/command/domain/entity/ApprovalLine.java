package com.ddis.ddis_hr.eapproval.command.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "approval_line")
public class ApprovalLine {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long approvalLineId;

        @Column(nullable = false)
        private int step; // 결재 단계

        @Column(nullable = false)
        private String status; // 결재 상태 (예: 미결, 대기중 등)

        private LocalDateTime approvedAt; // 결재 일시

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private ApprovalType type; // 🔸 결재유형 (INTERNAL, COOPERATIVE 등)

        private String opinion; // 결재 의견

        private Long departmentId;
        private Long positionId;
        private Long jobId;
        private Long teamId;
        private Long rankId;

        @Column(nullable = true)
        private Long employeeId; // 결재자 사원번호

        @Column(name = "line_type", nullable = false)
        @Enumerated(EnumType.STRING)
        private ApprovalLineType lineType; // 🔸 결재라인유형 (ACTUAL, TEMPLATE)

        private LocalDateTime viewedAt; // 열람일시

        @Column(nullable = false)
        private Long docId; // 기안문서번호

        @Column(nullable = false)
        private Long formId; // 기안양식번호
}
