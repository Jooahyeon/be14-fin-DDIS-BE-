package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveHistoryQueryDTO {
    private Long historyId;
    private String leaveType;
    private LocalDate requestDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private double leaveDays;
    private String approvalStatus;
    private String rejectReason;
}
