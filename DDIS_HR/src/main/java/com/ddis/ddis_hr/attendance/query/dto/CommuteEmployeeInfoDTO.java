package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;

@Data
public class CommuteEmployeeInfoDTO {

    private Long employeeId;
    private String employeeName;
    private String rankName;
    private String headName;
    private String departmentName;
    private String teamName;

}
