package com.ddis.ddis_hr.attendance.command.application.service;

public interface AttendanceCommandService {

    void checkIn(Long employeeId);

    void checkOut(Long employeeId);

}
