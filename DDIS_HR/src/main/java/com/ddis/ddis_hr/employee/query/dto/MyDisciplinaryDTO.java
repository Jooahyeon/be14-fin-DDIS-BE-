package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyDisciplinaryDTO {
    private Integer disciplinaryId;
    private String disciplinaryDescription;
    private Date disciplinaryDate;
    private String disciplinaryFileName;
    private String disciplinaryFilePath;
    private String disciplinaryFileSize;

    private Long employeeId;
}
