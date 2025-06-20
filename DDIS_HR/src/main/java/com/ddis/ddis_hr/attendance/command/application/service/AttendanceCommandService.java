package com.ddis.ddis_hr.attendance.command.application.service;

import com.ddis.ddis_hr.attendance.command.application.dto.AttendanceCorrectionRequestDTO;
import com.ddis.ddis_hr.attendance.command.application.dto.MeetingScheduleRequestDTO;
import com.ddis.ddis_hr.attendance.command.application.dto.OvertimeRequestDTO;
import com.ddis.ddis_hr.attendance.command.application.dto.PersonalScheduleRequestDTO;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.Meeting;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.PersonalSchedule;

public interface AttendanceCommandService {

    void checkIn(Long employeeId);

    void checkOut(Long employeeId);

    PersonalSchedule personalScheduleRegister(PersonalScheduleRequestDTO dto, Long employeeId);

    void updatePersonalSchedule(Long id, PersonalScheduleRequestDTO dto);

    void deletePersonalSchedule(Long id);

    Meeting MeetingScheduleRegister(MeetingScheduleRequestDTO dto, Long employeeId, Long teamId);

    void updateMeetingSchedule(Long id, MeetingScheduleRequestDTO dto);

    void deleteMeetingSchedule(Long id);

    void requestCorrection(Long employeeId, AttendanceCorrectionRequestDTO dto);

    void approveCorrection(Long attendanceId);

    void rejectCorrection(Long attendanceId, String rejectReason);

    void handleOvertimeRequest(OvertimeRequestDTO dto, Long employeeId);
}
