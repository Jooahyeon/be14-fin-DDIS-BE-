package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
// 기안문 상세조회 DTO 이거로 쓰임!!!!!!!!!!!!!!!!

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DraftDetailResponseQueryDTO {
    private int docId;                              // 문서번호
    private String docTitle;
    private String team;                            // 팀이름
    private String position;                        // 직책명
    private String drafter;                         // 기안자 이름
    private LocalDateTime date;                     // 기안일
    private int keepYear;                           // 보존연한
    private String receiver;                        // 수신자 이름
    private String referer;                         // 참조자 이름
    private String docContent;                      // 원본 JSON 문자열

    private ContentQueryDTO contentDto;             // 파싱한 객체

    private List<ApprovalLineQueryDTO> approvalLine;     // 결재라인 DTO

    public void setContentDto(ContentQueryDTO contentDto) {
        this.contentDto = contentDto;
    }
}
