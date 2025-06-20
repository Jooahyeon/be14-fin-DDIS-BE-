package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;

@Data
public class AllCommuteCorrectionQueryDTO {

    private Long attendanceId;
    private Long employeeId;
    private String employeeName;
    private String headName;
    private String departmentName;
    private String teamName;
    private String rankName;
    private String approvalStatus;
    private String workDate;
    private String requestTime;
    private String beforeCheckInTime;
    private String requestedTimeChange;
    private String processedTime;
    private String reason;
    private String rejectReason;

}
