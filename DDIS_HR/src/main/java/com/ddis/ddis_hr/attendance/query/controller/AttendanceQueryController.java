package com.ddis.ddis_hr.attendance.query.controller;

import com.ddis.ddis_hr.attendance.query.dto.*;
import com.ddis.ddis_hr.attendance.query.service.AttendanceQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceQueryController {

    private final AttendanceQueryService attendanceQueryService;

    @GetMapping("/calendar/personal")
    public ResponseEntity<List<PersonalCalendarQueryDTO>> getMyCalendar(@AuthenticationPrincipal CustomUserDetails user) {
        List<PersonalCalendarQueryDTO> result = attendanceQueryService.getMyCalendar(user.getEmployeeId());
        return ResponseEntity.ok(result); // HTTP 200 OK
    }

    @GetMapping("/calendar/team")
    public ResponseEntity<List<TeamCalendarQueryDTO>> getTeamCalendar(@AuthenticationPrincipal CustomUserDetails user) {
        List<TeamCalendarQueryDTO> result = attendanceQueryService.getTeamCalendar(user.getEmployeeId());
        return ResponseEntity.ok(result); // HTTP 200 OK
    }

    @GetMapping("/meeting/today")
    public ResponseEntity<List<MeetingQueryDTO>> getTodayMeetings(@AuthenticationPrincipal CustomUserDetails user) {
        List<MeetingQueryDTO> meetings = attendanceQueryService.getTodayMeetings(user.getEmployeeId());
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/schedule/today")
    public ResponseEntity<List<PersonalScheduleQueryDTO>> getTodaySchedules(@AuthenticationPrincipal CustomUserDetails user) {
        List<PersonalScheduleQueryDTO> schedules = attendanceQueryService.getTodaySchedules(user.getEmployeeId());
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/status/team")
    public ResponseEntity<Map<String, Object>> getTeamStatus(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        Long teamId = user.getTeamId();

        String teamName = attendanceQueryService.getTeamName(teamId);

        List<TeamWorkStatusQueryDTO> statuses = attendanceQueryService.getTeamWorkStatus(employeeId);

        Map<String, Object> response = new HashMap<>();
        response.put("teamName", teamName);
        response.put("statuses", statuses);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/me")
    public ResponseEntity<MyWorkStatusQueryDTO> getMyWorkStatus(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        MyWorkStatusQueryDTO dto = attendanceQueryService.getMyWorkStatus(employeeId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/overtime-summary")
    public ResponseEntity<WeeklyOvertimeSummaryQueryDTO> getWeeklyOvertimeSummary(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        WeeklyOvertimeSummaryQueryDTO dto = attendanceQueryService.getWeeklyOvertime(employeeId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/work-duration-summary")
    public ResponseEntity<WeeklyWorkDurationQueryDTO> getWeeklyWorkDurationSummary(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        WeeklyWorkDurationQueryDTO dto = attendanceQueryService.getWeeklyWorkDuration(employeeId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/leave/status/me")
    public ResponseEntity<LeaveStatusQueryDTO> getMyLeaveStatus(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        LeaveStatusQueryDTO status = attendanceQueryService.getLeaveStatus(employeeId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/leave/history/used/me")
    public ResponseEntity<List<LeaveHistoryQueryDTO>> getLeaveHistory(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        List<LeaveHistoryQueryDTO> historyList = attendanceQueryService.getLeaveHistory(employeeId);
        return ResponseEntity.ok(historyList);
    }

    @GetMapping("/leave/history/request/me")
    public ResponseEntity<List<LeaveHistoryQueryDTO>> getPendingLeaveRequests(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        List<LeaveHistoryQueryDTO> result = attendanceQueryService.getPendingLeaveRequests(employeeId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/leave/history/used/all")
    public ResponseEntity<List<AllLeaveHistoryQueryDTO>> getAllLeaveUsedList() {
        List<AllLeaveHistoryQueryDTO> result = attendanceQueryService.getAllLeaveUsedList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/leave/history/request/all")
    public ResponseEntity<List<AllLeaveHistoryQueryDTO>> getAllLeavePendingList() {
        List<AllLeaveHistoryQueryDTO> result = attendanceQueryService.getAllLeavePendingList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/commute/me")
    public ResponseEntity<List<MyCommuteQueryDTO>> getMyCommuteList(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        Long employeeId = user.getEmployeeId();
        List<MyCommuteQueryDTO> commuteList = attendanceQueryService.getMyCommuteList(employeeId, startDate, endDate);
        return ResponseEntity.ok(commuteList);
    }

    @GetMapping("/commute/summary/all")
    public ResponseEntity<List<AllCommuteSummaryDTO>> getAllCommuteSummary(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        List<AllCommuteSummaryDTO> result = attendanceQueryService.getAllCommuteSummaryList(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/commute/{employeeId}")
    public ResponseEntity<CommuteDetailDTO> getCommuteDetail(
            @PathVariable Long employeeId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return ResponseEntity.ok(attendanceQueryService.getCommuteDetail(employeeId, startDate, endDate));
    }

    @GetMapping("/correction/history/process/me")
    public ResponseEntity<List<MyCommuteCorrectionQueryDTO>> getCommuteCorrectionHistory(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        List<MyCommuteCorrectionQueryDTO> list = attendanceQueryService.getCorrectionHistory(employeeId);
        return ResponseEntity.ok(list);
    }






}
