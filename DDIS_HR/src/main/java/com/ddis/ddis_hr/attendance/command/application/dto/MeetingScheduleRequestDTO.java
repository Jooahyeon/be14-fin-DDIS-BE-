package com.ddis.ddis_hr.attendance.command.application.dto;

import lombok.Data;

@Data
public class MeetingScheduleRequestDTO {

    private String meetingDate;
    private String meetingTitle;
    private String meetingTime;

}