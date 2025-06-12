package com.ddis.ddis_hr.attendance.command.application.dto;

import lombok.Data;

@Data
public class PersonalScheduleRequestDTO {

    private String scheduleDate;
    private String scheduleTitle;
    private String scheduleTime;

}