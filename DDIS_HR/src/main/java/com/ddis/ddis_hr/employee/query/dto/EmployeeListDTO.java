package com.ddis.ddis_hr.employee.query.dto;

import java.time.LocalDate;
import lombok.*;
import org.apache.ibatis.type.Alias;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Alias("EmployeeListDTO")
public class EmployeeListDTO {
    private Long employeeId;
    private String employeeName;
    private LocalDate employeeBirth;
    private String positionName;
    private String rankName;
    private String jobName;
    private String headName;
    private String departmentName;
    private String teamName;
}
