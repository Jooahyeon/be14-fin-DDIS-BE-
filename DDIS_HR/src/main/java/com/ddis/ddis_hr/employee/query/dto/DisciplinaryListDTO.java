package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DisciplinaryListDTO {
    private Integer disciplinaryId;
    private String  disciplinaryDescription;
    private Date    disciplinaryDate;

    private List<DisciplinaryFileDTO> fileList;
    private Long    employeeId;
    private String  employeeName;
}
