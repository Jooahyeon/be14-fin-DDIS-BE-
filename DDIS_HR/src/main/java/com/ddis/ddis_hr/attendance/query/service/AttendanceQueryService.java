package com.ddis.ddis_hr.attendance.query.service;

import com.ddis.ddis_hr.attendance.query.dto.*;
import java.util.List;

public interface AttendanceQueryService {

    EmployeeInfoQueryDTO findById(Long employeeId);

    List<PersonalCalendarQueryDTO> getMyCalendar(Long employeeId);

    List<TeamCalendarQueryDTO> getTeamCalendar(Long employeeId);

    List<MeetingQueryDTO> getTodayMeetings(Long employeeId);

    List<TeamWorkStatusQueryDTO> getTeamWorkStatus(Long employeeId);

    String getTeamName(Long teamId);
}
