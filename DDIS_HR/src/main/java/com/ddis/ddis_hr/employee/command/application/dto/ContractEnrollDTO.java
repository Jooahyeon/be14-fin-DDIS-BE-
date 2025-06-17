package com.ddis.ddis_hr.employee.command.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ContractEnrollDTO {
    private String  employeeName;
    private String contractDescrip;
    private LocalDate requestDate;
    private LocalDate contractDate;
    private LocalDate endDate;
    private Long   employeeId;


    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class FileMeta {
        private String fileName;
        private String fileUrl;
        private Long   fileSize;
    }

    private List<FileMeta> files;
}

