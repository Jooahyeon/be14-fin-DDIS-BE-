package com.ddis.ddis_hr.eapproval.command.application.dto;

import com.ddis.ddis_hr.eapproval.command.domain.entity.DraftDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 기안 생성 요청용 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class DraftCreateCommandDTO {
    private String title;
    private String docContent;
    private int retentionPeriod;
    private LocalDate expirationDate;
    private String docStatus;
    private LocalDateTime createdAt;
    private LocalDateTime submittedAt;
    private LocalDateTime draftSavedAt;
    private LocalDateTime finalApprovalAt;
    private LocalDateTime deletedAt;
    private int draftVersion;
    private Long formId;
    private Long employeeId;
    private List<ApprovalLineDTO> approvalLines;
    private List<Long> approvers;
    private List<Long> cooperators;
    private List<Long> receivers;
    private List<Long> ccs;

    // ★ 첨부파일을 한 번에 담는 필드들
    private List<String> attachmentKeys;        // S3 key 리스트
    private List<String> originalFileNames;     // 원본 파일명 리스트
    private List<String> fileTypes;             // MIME 타입 리스트
    private List<Long> fileSizes;               // 파일 사이즈 리스트

    public DraftDocument toEntity() {
        LocalDate baseDate = (this.submittedAt != null
                ? this.submittedAt.toLocalDate()
                : LocalDate.now());
        LocalDate calculatedExpiration = baseDate.plusYears(this.retentionPeriod);

        return DraftDocument.builder()
                .docTitle(this.title)
                .docContent(this.docContent)
                .preservePeriod(this.retentionPeriod)
                .expirationDate(calculatedExpiration)
                .docStatus(this.docStatus != null ? this.docStatus : "대기중")
                .createdAt(this.createdAt != null ? this.createdAt : LocalDateTime.now())
                .submittedAt(this.submittedAt)
                .draftSavedAt(this.draftSavedAt)
                .finalApprovalAt(this.finalApprovalAt)
                .deletedAt(this.deletedAt)
                .draftVersion(this.draftVersion > 0 ? this.draftVersion : 1)
                .formId(this.formId != null ? this.formId : 1)
                .employeeId(this.employeeId)
                .build();
    }
}