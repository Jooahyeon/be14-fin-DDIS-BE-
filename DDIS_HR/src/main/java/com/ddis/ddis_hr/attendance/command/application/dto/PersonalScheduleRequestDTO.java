package com.ddis.ddis_hr.attendance.command.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PersonalScheduleRequestDTO {

    private String scheduleDate;
    private String scheduleTitle;
    private String scheduleTime;

}