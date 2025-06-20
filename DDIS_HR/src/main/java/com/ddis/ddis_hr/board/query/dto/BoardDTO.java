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


    private List<BoardFileDTO> fileList;
}