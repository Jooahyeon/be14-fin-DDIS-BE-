package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;

@Data
public class AllCommuteSummaryDTO {

    private Long employeeId;
    private String employeeName;
    private String rankName;
    private String teamName;
    private String departmentName;
    private String headName;
    private int annualCount;
    private int halfCount;
    private int businessTripCount;
    private int outsideCount;
    private int overtimeCount;
    private int lateCount;
    private int absentCount;
    private int presentCount;
    private int totalWorkTime;

}
