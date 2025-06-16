package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
// 징계 상세 DTO
public class DisciplinaryDTO {
    private Long                      disciplinaryId;
    private String                    disciplinaryDescription;
    private LocalDate disciplinaryDate;
    private Long                      employeeId;
    private String                    employeeName;
    private List<DisciplinaryFileDTO> fileList;
}
