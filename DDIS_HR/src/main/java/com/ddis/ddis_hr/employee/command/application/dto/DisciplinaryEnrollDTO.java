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
    private Long employeeId;               // 사원번호(필수)
    private String employeeName;           // 사원명(필수)
    private String disciplinaryDescription;// 징계내용(필수)
    private LocalDate disciplinaryDate;    // 징계일자(필수)
    // S3에 upload-url 로 업로드한 후 받아온 key, 원본이름, 크기를 담아서 보냅니다.
    private List<FileMeta> files;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileMeta {
        private String fileName;    // 원본 파일명
        private String fileUrl;     // S3 키
        private Long fileSize;      // 파일 크기(바이트)
    }
}


