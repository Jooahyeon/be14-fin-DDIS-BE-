package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MeetingQueryDTO {

    private Long teamId;
    private Long meetingId;
    private LocalDate meetingDate;   // LocalDate 또는 String
    private String meetingTitle;
    private String meetingTime;

}
