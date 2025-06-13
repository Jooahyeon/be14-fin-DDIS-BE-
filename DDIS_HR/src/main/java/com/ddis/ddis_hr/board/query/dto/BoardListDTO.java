package com.ddis.ddis_hr.board.query.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardListDTO {
    private Long          boardId;        // 공지사항번호
    private String        boardTitle;     // 제목
    private LocalDateTime boardCreateAt; // 작성일자

    private String employeeName;
}
