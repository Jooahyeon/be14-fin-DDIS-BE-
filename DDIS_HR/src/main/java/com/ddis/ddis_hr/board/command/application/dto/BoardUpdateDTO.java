package com.ddis.ddis_hr.board.command.application.dto;

import lombok.Data;
import java.util.List;

@Data
public class BoardUpdateDTO {
    private String boardTitle;
    private String boardContent;
    private List<FileDTO> files;

    @Data
    public static class FileDTO {
        private String key;
        private String fileName;
        private Long fileSize;
    }
}
