package com.ddis.ddis_hr.attendance.command.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OvertimeRequestDTO {

    private String type; // 시간외근무, 야간근무, 휴일근무
    private Integer duration; // 시간(숫자), 휴일근무는 무시
    private String date; // 휴일근무일 경우의 날짜 (YYYY-MM-DD)

}
