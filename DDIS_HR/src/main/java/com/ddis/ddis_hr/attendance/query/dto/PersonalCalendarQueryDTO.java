package com.ddis.ddis_hr.attendance.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCalendarQueryDTO {

    private String type; // "personal" or "attendance"
    private Long personalScheduleId;
    private String title; // 일정명 (personal)
    private String time;  // 일정 시간 (personal)
    private String status; // 근무 상태명 (attendance)
    private String employee; // 사원 이름 (attendance)
    private LocalDate date; // 일정 날짜

}
