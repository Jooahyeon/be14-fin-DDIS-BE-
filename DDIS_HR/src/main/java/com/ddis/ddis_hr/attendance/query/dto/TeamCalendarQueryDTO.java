package com.ddis.ddis_hr.attendance.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamCalendarQueryDTO {

    private String type; // "meeting" or "attendance"
    private Long meetingId;
    private String title; // 회의명 (meeting)
    private String time;  // 회의 시간 (meeting)
    private String status; // 근무 상태명 (attendance)
    private String employee; // 사원 이름 (attendance)
    private LocalDate date; // 일정 날짜

}
