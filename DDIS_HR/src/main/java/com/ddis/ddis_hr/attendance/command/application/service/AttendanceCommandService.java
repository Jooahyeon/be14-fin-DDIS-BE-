package com.ddis.ddis_hr.attendance.command.application.service;

import com.ddis.ddis_hr.attendance.command.application.dto.AttendanceCorrectionRequestDTO;
import com.ddis.ddis_hr.attendance.command.application.dto.MeetingScheduleRequestDTO;
import com.ddis.ddis_hr.attendance.command.application.dto.PersonalScheduleRequestDTO;

public interface AttendanceCommandService {

    void checkIn(Long employeeId);

    void checkOut(Long employeeId);

    void personalScheduleRegister(PersonalScheduleRequestDTO dto, Long employeeId);

    void MeetingScheduleRegister(MeetingScheduleRequestDTO dto, Long employeeId, Long teamId);

    void requestCorrection(Long employeeId, AttendanceCorrectionRequestDTO dto);

    void approveCorrection(Long attendanceId);

    void rejectCorrection(Long attendanceId, String rejectReason);
}
