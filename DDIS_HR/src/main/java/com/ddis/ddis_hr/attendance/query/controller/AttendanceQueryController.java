package com.ddis.ddis_hr.attendance.query.controller;

import com.ddis.ddis_hr.attendance.query.dto.*;
import com.ddis.ddis_hr.attendance.query.service.AttendanceQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceQueryController {

    private final AttendanceQueryService attendanceQueryService;

    // 개인 캘린더 조회
    @GetMapping("/calendar/personal")
    public ResponseEntity<List<PersonalCalendarQueryDTO>> getMyCalendar(@AuthenticationPrincipal CustomUserDetails user) {
        List<PersonalCalendarQueryDTO> result = attendanceQueryService.getMyCalendar(user.getEmployeeId());
        return ResponseEntity.ok(result); // HTTP 200 OK
    }

    // 팀 캘린더 조회
    @GetMapping("/calendar/team")
    public ResponseEntity<List<TeamCalendarQueryDTO>> getTeamCalendar(@AuthenticationPrincipal CustomUserDetails user) {
        List<TeamCalendarQueryDTO> result = attendanceQueryService.getTeamCalendar(user.getEmployeeId());
        return ResponseEntity.ok(result); // HTTP 200 OK
    }

    // 오늘 회의 일정 조회
    @GetMapping("/meeting/today")
    public ResponseEntity<List<MeetingQueryDTO>> getTodayMeetings(@AuthenticationPrincipal CustomUserDetails user) {
        List<MeetingQueryDTO> meetings = attendanceQueryService.getTodayMeetings(user.getEmployeeId());
        return ResponseEntity.ok(meetings);
    }

    // 오늘 개인 일정 조회
    @GetMapping("/schedule/today")
    public ResponseEntity<List<PersonalScheduleQueryDTO>> getTodaySchedules(@AuthenticationPrincipal CustomUserDetails user) {
        List<PersonalScheduleQueryDTO> schedules = attendanceQueryService.getTodaySchedules(user.getEmployeeId());
        return ResponseEntity.ok(schedules);
    }

    // 오늘 팀 근무 현황 조회
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

    // 오늘 내 근무 현황 조회
    @GetMapping("/status/me")
    public ResponseEntity<MyWorkStatusQueryDTO> getMyWorkStatus(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        MyWorkStatusQueryDTO dto = attendanceQueryService.getMyWorkStatus(employeeId);
        return ResponseEntity.ok(dto);
    }

    // 주간 초과 근무 시간 조회
    @GetMapping("/overtime-summary")
    public ResponseEntity<WeeklyOvertimeSummaryQueryDTO> getWeeklyOvertimeSummary(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        WeeklyOvertimeSummaryQueryDTO dto = attendanceQueryService.getWeeklyOvertime(employeeId);
        return ResponseEntity.ok(dto);
    }

    // 주간 근무 시간 조회
    @GetMapping("/work-duration-summary")
    public ResponseEntity<WeeklyWorkDurationQueryDTO> getWeeklyWorkDurationSummary(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        WeeklyWorkDurationQueryDTO dto = attendanceQueryService.getWeeklyWorkDuration(employeeId);
        return ResponseEntity.ok(dto);
    }

    // 내 연차 현황 조회
    @GetMapping("/leave/status/me")
    public ResponseEntity<LeaveStatusQueryDTO> getMyLeaveStatus(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        LeaveStatusQueryDTO status = attendanceQueryService.getLeaveStatus(employeeId);
        return ResponseEntity.ok(status);
    }

    // 내 연차 사용 내역 조회
    @GetMapping("/leave/history/used/me")
    public ResponseEntity<List<LeaveHistoryQueryDTO>> getLeaveHistory(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        List<LeaveHistoryQueryDTO> historyList = attendanceQueryService.getLeaveHistory(employeeId);
        return ResponseEntity.ok(historyList);
    }

    // 내 연차 신청 내역 조회
    @GetMapping("/leave/history/request/me")
    public ResponseEntity<List<LeaveHistoryQueryDTO>> getPendingLeaveRequests(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        List<LeaveHistoryQueryDTO> result = attendanceQueryService.getPendingLeaveRequests(employeeId);
        return ResponseEntity.ok(result);
    }

    // 전체 연차 사용 내역 조회 (인사)
    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/leave/history/used/all")
    public ResponseEntity<List<AllLeaveHistoryQueryDTO>> getAllLeaveUsedList() {
        List<AllLeaveHistoryQueryDTO> result = attendanceQueryService.getAllLeaveUsedList();
        return ResponseEntity.ok(result);
    }

    // 전체 연차 신청 내역 조회 (인사)
    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/leave/history/request/all")
    public ResponseEntity<List<AllLeaveHistoryQueryDTO>> getAllLeavePendingList() {
        List<AllLeaveHistoryQueryDTO> result = attendanceQueryService.getAllLeavePendingList();
        return ResponseEntity.ok(result);
    }

    // 내 출퇴근 내역 조회
    @GetMapping("/commute/me")
    public ResponseEntity<List<MyCommuteQueryDTO>> getMyCommuteList(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        Long employeeId = user.getEmployeeId();
        List<MyCommuteQueryDTO> commuteList = attendanceQueryService.getMyCommuteList(employeeId, startDate, endDate);
        return ResponseEntity.ok(commuteList);
    }

    // 전체 출퇴근 내역 요약 조회 (인사)
    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/commute/summary/all")
    public ResponseEntity<List<AllCommuteSummaryDTO>> getAllCommuteSummary(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        List<AllCommuteSummaryDTO> result = attendanceQueryService.getAllCommuteSummaryList(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    // 특정 사원 출퇴근 내역 조회 (인사)
    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/commute/{employeeId}")
    public ResponseEntity<CommuteDetailDTO> getCommuteDetail(
            @PathVariable Long employeeId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return ResponseEntity.ok(attendanceQueryService.getCommuteDetail(employeeId, startDate, endDate));
    }

    // 내 출근 정정 처리 내역 조회
    @GetMapping("/correction/history/process/me")
    public ResponseEntity<List<MyCommuteCorrectionQueryDTO>> getCommuteCorrectionHistory(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        List<MyCommuteCorrectionQueryDTO> list = attendanceQueryService.getCorrectionHistory(employeeId);
        return ResponseEntity.ok(list);
    }

    // 내 출근 정정 신청 내역 조회
    @GetMapping("/correction/history/request/me")
    public ResponseEntity<List<MyCommuteCorrectionQueryDTO>> getCorrectionRequestHistory(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        List<MyCommuteCorrectionQueryDTO> list = attendanceQueryService.getCorrectionRequestHistory(employeeId);
        return ResponseEntity.ok(list);
    }

    // 전체 출근 정정 처리 내역 조회 (인사)
    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/correction/history/process/all")
    public ResponseEntity<List<AllCommuteCorrectionQueryDTO>> getAllCorrectionHistory() {
        List<AllCommuteCorrectionQueryDTO> list = attendanceQueryService.getAllCorrectionHistory();
        return ResponseEntity.ok(list);
    }

    // 전체 출근 정정 신청 내역 조회 (인사)
    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/correction/history/request/all")
    public ResponseEntity<List<AllCommuteCorrectionQueryDTO>> getAllCorrectionRequestHistory() {
        List<AllCommuteCorrectionQueryDTO> list = attendanceQueryService.getAllCorrectionRequestHistory();
        return ResponseEntity.ok(list);
    }

}
