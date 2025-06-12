package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;

@Data
public class WeeklyOvertimeSummaryQueryDTO {

    private int regularOvertime; // 시간외근무
    private int nightOvertime;   // 야간근무
    private int holidayOvertime; // 휴일근무
    private int totalOvertime;   // 총합

    public void calculateTotal() {
        this.totalOvertime = regularOvertime + nightOvertime + holidayOvertime;
    }

}
