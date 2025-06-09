package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class MyWorkStatusQueryDTO {

    private String employeeName;
    private String rankName;
    private String workStatusName;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;

}
