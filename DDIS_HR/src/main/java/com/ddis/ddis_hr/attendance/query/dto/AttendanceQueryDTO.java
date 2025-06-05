package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AttendanceQueryDTO {

    private Long attendanceId;
    private Long employeeId;
    private String workStatusId;
    private LocalDate workDate;

    // JOIN 결과 받기 위한 필드
    private String employeeName;
    private String workStatusName;

}
