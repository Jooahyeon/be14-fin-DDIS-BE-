package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;

@Data
public class MyCommuteCorrectionQueryDTO {

    private String approvalStatus;
    private String requestTime;
    private String beforeCheckInTime;
    private String requestedTimeChange;
    private String processedTime;
    private String reason;
    private String rejectReason;

}
