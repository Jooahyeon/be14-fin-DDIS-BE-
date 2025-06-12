package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;

@Data
public class MyCommuteQueryDTO {

    private String workDate;
    private String workStatus;
    private String checkInTime;
    private String checkOutTime;
    private Integer workDuration;
    private Integer overtimeExtra;   // 시간외근무
    private Integer overtimeNight;   // 야간근무
    private Integer overtimeHoliday; // 휴일근무
    private Integer totalWorkTime;

}
