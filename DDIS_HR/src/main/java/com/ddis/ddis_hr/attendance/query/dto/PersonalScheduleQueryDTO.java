package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PersonalScheduleQueryDTO {

        private Long personalScheduleId;
        private Long employeeId;
        private LocalDate scheduleDate;
        private String scheduleTitle;
        private String scheduleTime;
    }
