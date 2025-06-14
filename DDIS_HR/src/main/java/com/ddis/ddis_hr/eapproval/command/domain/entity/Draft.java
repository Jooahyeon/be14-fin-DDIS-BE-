package com.ddis.ddis_hr.eapproval.command.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 전자결재 기안문 엔티티
 */
@Entity
@Table(name = "draft_documents") // ← 실제 테이블명으로 변경
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)    // ← 여기에도!
public class Draft {

    /** 문서번호 (PK, AUTO_INCREMENT) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id")
    private Long docId;

    /** 제목 */
    @Column(name = "doc_title", nullable = false)
    private String docTitle;

    /** 문서 본문(JSON) */
    @Lob
    @Column(name = "doc_content", columnDefinition = "LONGTEXT")
    private String docContent;

    /** 보존 기간(년) */
    @Column(name = "preserve_period", nullable = false)
    private int preservePeriod;

    /** 만료일 */
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    /** 상태 (예: 대기중, 승인, 반려 등) */
    @Column(name = "doc_status", nullable = false)
    private String docStatus;

    /** 작성일 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** 상신일 */
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    /** 임시저장일 */
    @Column(name = "draft_saved_at")
    private LocalDateTime draftSavedAt;

    /** 최종결재완료일 */
    @Column(name = "final_approval_at")
    private LocalDateTime finalApprovalAt;

    /** 삭제일 */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /** 차수 */
    @Column(name = "draft_version", nullable = false)
    private int draftVersion;

    /** 선택한 양식 ID (FK) */
    @Column(name = "form_id", nullable = false)
    private Long formId;

    /** 기안자(사원번호) */
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    public Draft withStatus(String newStatus) {
        return this.toBuilder()
                .docStatus(newStatus)
                .build();
    }
}