package com.ddis.ddis_hr.attendance.command.application.dto;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.PersonalSchedule;
import lombok.Data;

@Data
public class PersonalScheduleResponseDTO {

    private Long id;
    private String scheduleDate;
    private String scheduleTitle;
    private String scheduleTime;

    public PersonalScheduleResponseDTO(PersonalSchedule schedule) {
        this.id = schedule.getPersonalScheduleId();
        this.scheduleDate = schedule.getScheduleDate().toString();
        this.scheduleTitle = schedule.getScheduleTitle();
        this.scheduleTime = schedule.getScheduleTime();
    }
}
