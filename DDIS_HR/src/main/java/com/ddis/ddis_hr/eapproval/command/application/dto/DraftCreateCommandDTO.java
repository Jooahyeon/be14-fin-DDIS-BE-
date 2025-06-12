package com.ddis.ddis_hr.eapproval.command.application.dto;

import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
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
    private String title;                // 문서 제목
    private String docContent;          // JSON 전체 내용
    private int retentionPeriod;        // 보존 연한
    private LocalDate expirationDate;   // 만료일
    private String docStatus;           // 상태 (ex. "대기중")
    private LocalDateTime createdAt;    // 작성일
    private LocalDateTime submittedAt;  // 상신일
    private LocalDateTime draftSavedAt; // 임시저장일
    private LocalDateTime finalApprovalAt; // 최종결재일
    private LocalDateTime deletedAt;    // 삭제일
    private int draftVersion;           // 차수
    private Long formId;                // 양식 ID
    private Long employeeId;            // 작성자 사번

    public Draft toEntity() {
        // 1) 기준 날짜 계산: submittedAt이 있으면 그 날짜, 없으면 지금 날짜
        LocalDate baseDate = (this.submittedAt != null
                ? this.submittedAt.toLocalDate()
                : LocalDate.now());

        // 2) 보존 연한만큼 년 추가
        LocalDate calculatedExpiration = baseDate.plusYears(this.retentionPeriod);

        return Draft.builder()
                .docTitle(this.title)
                .docContent(this.docContent)
                .preservePeriod(this.retentionPeriod)
                .expirationDate(calculatedExpiration)
                .docStatus(this.docStatus != null ? this.docStatus : "대기중")
                .createdAt(this.createdAt != null ? this.createdAt : LocalDateTime.now())
                .submittedAt(LocalDateTime.now())
                .draftSavedAt(this.draftSavedAt)
                .finalApprovalAt(this.finalApprovalAt)
                .deletedAt(this.deletedAt)
                .draftVersion(this.draftVersion > 0 ? this.draftVersion : 1)
                .formId(this.formId != null ? this.formId : 1)
                .employeeId(this.employeeId)
                .build();
    }
}