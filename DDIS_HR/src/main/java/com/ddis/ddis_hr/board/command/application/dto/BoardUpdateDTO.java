package com.ddis.ddis_hr.board.command.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardUpdateDTO {
    private String boardTitle;
    private String boardContent;
    private List<FileDTO> files;

    @Getter
    @Setter
    public static class FileDTO {
        private String key;
        private String fileName;
        private Long fileSize;
    }
}
