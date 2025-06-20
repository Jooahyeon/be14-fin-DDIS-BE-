package com.ddis.ddis_hr.employee.command.application.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisciplinaryEnrollDTO {
    private Long employeeId;
    private String employeeName;
    private String disciplinaryDescription;
    private LocalDate disciplinaryDate;
    private List<FileMeta> files;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileMeta {
        private String fileName;
        private String fileUrl;
        private Long fileSize;
    }
}


