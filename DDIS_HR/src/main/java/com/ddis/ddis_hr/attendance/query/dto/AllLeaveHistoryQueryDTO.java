package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AllLeaveHistoryQueryDTO {

    private Long historyId;
    private String employeeCode;
    private String employeeName;
    private String headName;
    private String departmentName;
    private String teamName;
    private String rankName;
    private String leaveType;
    private String approvalStatus;
    private LocalDate requestDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private double leaveDays;
    private String reason;
    private String rejectReason;

}
