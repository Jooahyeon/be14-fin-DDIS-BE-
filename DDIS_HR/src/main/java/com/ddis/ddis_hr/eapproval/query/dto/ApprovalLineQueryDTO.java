package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ApprovalLineQueryDTO {

    private int id;                         // 결재라인번호
    private int step;                       // 결재 순번
    private String name;                    // 결재자 이름
    private Long employeeId;                // 결재자 사번
    private String team;                    // 팀 이름
    private String position;                // 직책명
    private String rankName;                // 기안자 직책명
    private String approvalLine_rankName;   // 결재자 직책명
    private String status;                  // 결재상태
    private String type;                    // 결재유형
    private String lineType;                // 결재라인 유형
    private LocalDateTime submitDate;       // 기안상신일
    private LocalDateTime approveDate;      // 결재일
    private String comment;                 // 의견
}
