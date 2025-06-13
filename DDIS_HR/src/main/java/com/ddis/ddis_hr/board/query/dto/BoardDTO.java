package com.ddis.ddis_hr.board.query.dto;

import lombok.*;


import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {
    private String          boardTitle;
    private String          boardContent;
    private LocalDateTime   boardCreateAt;
    private String          boardFileName;
    private String          boardFile;
    private Integer         boardFileSize;

    private Long            employeeId;
}
