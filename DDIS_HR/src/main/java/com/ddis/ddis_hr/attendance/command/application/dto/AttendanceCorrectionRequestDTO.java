package com.ddis.ddis_hr.attendance.command.application.dto;

import lombok.Data;

@Data
public class AttendanceCorrectionRequestDTO {

    private String requestedDate;
    private String requestedTimeChange;
    private String reason;

}
