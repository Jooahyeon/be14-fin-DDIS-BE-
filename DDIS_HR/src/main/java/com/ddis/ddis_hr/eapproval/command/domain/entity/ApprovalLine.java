package com.ddis.ddis_hr.eapproval.command.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder(toBuilder = true)    // ← 여기!
@Table(name = "approval_line")
public class ApprovalLine {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "approval_line_id")
        private Long approvalLineId;

        @Column(name = "step", nullable = false)
        private int step; // 결재 단계

        @Column(name = "status", nullable = false)
        private String status; // 결재 상태 (예: 미결, 대기중 등)

        @Column(name = "approved_at")
        private LocalDateTime approvedAt; // 결재 일시

        @Column(name = "type", nullable = false)
        private String type; // 🔸 결재유형 (INTERNAL, COOPERATIVE 등)

        @Column(name = "opinion")
        private String opinion; // 결재 의견

        @Column(name = "department_id")
        private Long departmentId;
        @Column(name = "position_id")
        private Long positionId;
        @Column(name = "job_id")
        private Long jobId;
        @Column(name = "team_id")
        private Long teamId;
        @Column(name = "rank_id")
        private Long rankId;

        @Column(name = "employee_id", nullable = false)
        private Long employeeId; // 결재자 사원번호

        @Column(name = "line_type", nullable = false)
        @Enumerated(EnumType.STRING)
        private ApprovalLineType lineType; // 🔸 결재라인유형 (ACTUAL, TEMPLATE)

        @Column(name = "viewed_at")
        private LocalDateTime viewedAt; // 열람일시

        @Column(name = "doc_id", nullable = false)
        private Long docId; // 기안문서번호                    // FK → draft_documents.doc_id

        @Column(name = "form_id", nullable = false)
        private Long formId; // 기안양식번호



        public ApprovalLine asPending() {
                return this.toBuilder()
                        .status("미결")
                        .approvedAt(null)
                        .build();
        }
        public ApprovalLine asWaiting() {
                return this.toBuilder()
                        .status("대기중")
                        .build();
        }
}
