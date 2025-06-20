package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// 기안문 상세조회 DTO

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DraftDetailResponseQueryDTO {
    private Long docId;                              // 문서번호
    private Long drafterId;                          // 기안자id
    private String docStatus;                       // 문서상태
    private String docTitle;                        // 문서제목
    private String team;                            // 팀이름
    private String position;                        // 직책명
    private String drafter;                         // 기안자 이름
    private LocalDateTime date;                     // 기안일
    private LocalDateTime submitDate;
    private int keepYear;                           // 보존연한
    private String receiver;                        // 수신자 이름
    private String referer;                         // 참조자 이름
    private String docContent;                      // 원본 JSON 문자열
    private Long rankId;                            // 기안자 직급
    private String rankName;                        // 기안자 직급명
    private String role;                            // 역할

    private ContentQueryDTO contentDto;             // 파싱한 객체

    private List<ApprovalLineQueryDTO> approvalLine;     // 결재라인 DTO
    private List<FileQueryDTO> attachments;

    public void setContentDto(ContentQueryDTO contentDto) {
        this.contentDto = contentDto;
    }
}
