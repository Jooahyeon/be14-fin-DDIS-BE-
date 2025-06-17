package com.ddis.ddis_hr.attendance.command.application.dto;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.Meeting;
import lombok.Data;

@Data
public class MeetingScheduleResponseDTO {

    private Long id;
    private String MeetingDate;
    private String MeetingTitle;
    private String MeetingTime;

    public MeetingScheduleResponseDTO(Meeting meeting) {
        this.id = meeting.getMeetingId();
        this.MeetingDate = meeting.getMeetingDate().toString();
        this.MeetingTitle = meeting.getMeetingTitle();
        this.MeetingTime = meeting.getMeetingTime();
    }
}
