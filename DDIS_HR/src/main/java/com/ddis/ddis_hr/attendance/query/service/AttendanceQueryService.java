package com.ddis.ddis_hr.attendance.query.service;

import com.ddis.ddis_hr.attendance.query.dto.*;
import java.util.List;

public interface AttendanceQueryService {

    EmployeeInfoQueryDTO findById(Long employeeId);

    List<PersonalCalendarQueryDTO> getMyCalendar(Long employeeId);

    List<TeamCalendarQueryDTO> getTeamCalendar(Long employeeId);

    List<MeetingQueryDTO> getTodayMeetings(Long employeeId);

    List<PersonalScheduleQueryDTO> getTodaySchedules(Long employeeId);

    List<TeamWorkStatusQueryDTO> getTeamWorkStatus(Long employeeId);

    String getTeamName(Long teamId);

    MyWorkStatusQueryDTO getMyWorkStatus(Long employeeId);

    WeeklyOvertimeSummaryQueryDTO getWeeklyOvertime(Long employeeId);

    WeeklyWorkDurationQueryDTO getWeeklyWorkDuration(Long employeeId);

    LeaveStatusQueryDTO getLeaveStatus(Long employeeId);

    List<LeaveHistoryQueryDTO> getLeaveHistory(Long employeeId);

    List<LeaveHistoryQueryDTO> getPendingLeaveRequests(Long employeeId);

    List<AllLeaveHistoryQueryDTO> getAllLeaveUsedList();

    List<AllLeaveHistoryQueryDTO> getAllLeavePendingList();

    List<MyCommuteQueryDTO> getMyCommuteList(Long employeeId, String startDate, String endDate);

    List<AllCommuteSummaryDTO> getAllCommuteSummaryList(String startDate, String endDate);

    CommuteDetailDTO getCommuteDetail(Long employeeId, String startDate, String endDate);

    List<MyCommuteCorrectionQueryDTO> getCorrectionHistory(Long employeeId);

}
