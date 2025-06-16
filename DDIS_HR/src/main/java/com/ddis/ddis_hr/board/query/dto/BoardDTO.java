package com.ddis.ddis_hr.board.query.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {
    private String          boardTitle;
    private String          boardContent;
    private LocalDateTime boardCreateAt;
    private String            employeeName;

    // 아래 필드 제거: boardFileName, boardFile, boardFileSize
    // 대신 복수 파일 담을 리스트
    private List<BoardFileDTO> fileList;
}