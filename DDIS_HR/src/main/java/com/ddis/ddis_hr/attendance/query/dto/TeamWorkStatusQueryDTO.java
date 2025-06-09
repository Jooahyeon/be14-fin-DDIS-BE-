package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;

@Data
public class TeamWorkStatusQueryDTO {

    private Long employeeId;
    private String employeeName;
    private String positionName;
    private String profileImage;
    private String workStatusName;
    private Integer positionId;

}
