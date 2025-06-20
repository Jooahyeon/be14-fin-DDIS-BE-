package com.ddis.ddis_hr.eapproval.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DraftDTO {
    private Long docId;
    private String docTitle;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;     // 생성일
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime submittedAt;   // 상신일
    private String status;        // "대기중", "반려", "회수" 등
    private String type;          // 양식명
    private String writer;        // 기안자 이름
    private String approver;

}
