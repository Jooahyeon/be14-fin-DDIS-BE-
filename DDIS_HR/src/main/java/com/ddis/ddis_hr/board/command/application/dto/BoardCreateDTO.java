package com.ddis.ddis_hr.board.command.application.dto;

import lombok.Data;
import java.util.List;

@Data
public class BoardCreateDTO {
    private String boardTitle;
    private String boardContent;
    private List<FileDTO> files;

    @Data
    public static class FileDTO {
        private String key;       // S3 객체 키
        private String fileName;  // 원본 파일명
        private Long fileSize;
    }
}
