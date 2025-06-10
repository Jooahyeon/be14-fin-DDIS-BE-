package com.ddis.ddis_hr.attendance.command.application.service;

import com.ddis.ddis_hr.attendance.command.application.dto.PersonalScheduleRequestDTO;

public interface AttendanceCommandService {

    void checkIn(Long employeeId);

    void checkOut(Long employeeId);

    void personalScheduleRegister(PersonalScheduleRequestDTO dto, Long employeeId);

}
