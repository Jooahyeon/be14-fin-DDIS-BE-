package com.ddis.ddis_hr.attendance.query.service;

import com.ddis.ddis_hr.attendance.query.dao.AttendanceMapper;
import com.ddis.ddis_hr.attendance.query.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceQueryServiceImpl implements AttendanceQueryService {

    private final AttendanceMapper attendanceMapper;

    @Override
    public EmployeeInfoQueryDTO findById(Long employeeId) {
        return Optional.ofNullable(attendanceMapper.findById(employeeId))
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));
    }

    @Override
    public List<PersonalCalendarQueryDTO> getMyCalendar(Long employeeId) {
        EmployeeInfoQueryDTO employee = findById(employeeId);
        Long teamId = employee.getTeamId();

        List<PersonalCalendarQueryDTO> events = new ArrayList<>();

        for (PersonalScheduleQueryDTO ps : attendanceMapper.findPersonalSchedules(employeeId)) {
            events.add(new PersonalCalendarQueryDTO("personal", ps.getPersonalScheduleId(), ps.getScheduleTitle(), ps.getScheduleTime(), null, null, ps.getScheduleDate()));
        }

        List<String> validStatuses = List.of("LATE", "ABSENT", "BUSINESS_TRIP", "FIELD_WORK", "ANNUAL_LEAVE", "HALF_AM", "HALF_PM");
        for (AttendanceQueryDTO at : attendanceMapper.findWorkStatuses(teamId, employeeId, validStatuses)) {
            events.add(new PersonalCalendarQueryDTO("attendance",null, null, null, at.getWorkStatusName(), at.getEmployeeName(), at.getWorkDate()));
        }

        return events;
    }

    @Override
    public List<TeamCalendarQueryDTO> getTeamCalendar(Long employeeId) {
        EmployeeInfoQueryDTO employee = findById(employeeId);
        Long teamId = employee.getTeamId();

        List<TeamCalendarQueryDTO> events = new ArrayList<>();

        for (MeetingQueryDTO m : attendanceMapper.findMeetings(teamId)) {
            events.add(new TeamCalendarQueryDTO("meeting", m.getMeetingId(), m.getMeetingTitle(), m.getMeetingTime(), null, null, m.getMeetingDate()));
        }

        List<String> validStatuses = List.of("LATE", "ABSENT", "BUSINESS_TRIP", "FIELD_WORK", "ANNUAL_LEAVE", "HALF_AM", "HALF_PM");
        for (AttendanceQueryDTO at : attendanceMapper.findWorkStatuses(teamId, employeeId, validStatuses)) {
            events.add(new TeamCalendarQueryDTO("attendance", null, null, null, at.getWorkStatusName(), at.getEmployeeName(), at.getWorkDate()));
        }

        return events;
    }

    @Override
    public List<MeetingQueryDTO> getTodayMeetings(Long employeeId) {
        EmployeeInfoQueryDTO employee = attendanceMapper.findById(employeeId);
        Long teamId = employee.getTeamId();
        return attendanceMapper.findMeetingsToday(teamId, LocalDate.now());
    }

    @Override
    public List<PersonalScheduleQueryDTO> getTodaySchedules(Long employeeId) {
        EmployeeInfoQueryDTO employee = attendanceMapper.findById(employeeId);
        return attendanceMapper.findSchedulesToday(employeeId, LocalDate.now());
    }

    @Override
    public List<TeamWorkStatusQueryDTO> getTeamWorkStatus(Long employeeId) {
        EmployeeInfoQueryDTO employee = attendanceMapper.findById(employeeId);
        Long teamId = employee.getTeamId();
        String today = LocalDate.now().toString();
        return attendanceMapper.findTodayTeamStatuses(teamId, today);
    }

    @Override
    public String getTeamName(Long teamId) {
        return attendanceMapper.findTeamNameById(teamId);
    }

    @Override
    public MyWorkStatusQueryDTO getMyWorkStatus(Long employeeId) {
        String today = LocalDate.now().toString();
        return attendanceMapper.findMyWorkStatus(employeeId, today);
    }

    @Override
    public WeeklyOvertimeSummaryQueryDTO getWeeklyOvertime(Long employeeId) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        LocalDate startOfWeek = today.minusDays(dayOfWeek.getValue() % 7); // 일요일
        LocalDate endOfWeek = startOfWeek.plusDays(6); // 토요일

        WeeklyOvertimeSummaryQueryDTO result = attendanceMapper.findWeeklyOvertimeSummary(employeeId, startOfWeek, endOfWeek);
        result.calculateTotal();
        return result;
    }

    @Override
    public WeeklyWorkDurationQueryDTO getWeeklyWorkDuration(Long employeeId) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() % 7); // 일요일
        LocalDate endOfWeek = startOfWeek.plusDays(6); // 토요일

        return attendanceMapper.findWeeklyWorkDuration(employeeId, startOfWeek, endOfWeek);
    }

    @Override
    public LeaveStatusQueryDTO getLeaveStatus(Long employeeId) {
        return Optional.ofNullable(attendanceMapper.getLeaveStatus(employeeId))
                .orElseThrow(() -> new EntityNotFoundException("연차 정보가 없습니다."));
    }

    @Override
    public List<LeaveHistoryQueryDTO> getLeaveHistory(Long employeeId) {
        return attendanceMapper.getLeaveHistoryByEmployeeId(employeeId);
    }

    @Override
    public List<LeaveHistoryQueryDTO> getPendingLeaveRequests(Long employeeId) {
        return attendanceMapper.getPendingLeaveRequests(employeeId);
    }

    @Override
    public List<AllLeaveHistoryQueryDTO> getAllLeaveUsedList() {
        return attendanceMapper.getAllLeaveUsedList();
    }

    @Override
    public List<AllLeaveHistoryQueryDTO> getAllLeavePendingList() {
        return attendanceMapper.getAllLeavePendingList();
    }

    @Override
    public List<MyCommuteQueryDTO> getMyCommuteList(Long employeeId, String startDate, String endDate) {
        return attendanceMapper.getMyCommuteList(employeeId, startDate, endDate);
    }

    @Override
    public List<AllCommuteSummaryDTO> getAllCommuteSummaryList(String startDate, String endDate) {

        String startMonth = startDate.substring(0, 7);
        String endMonth = endDate.substring(0, 7);

        return attendanceMapper.getAllCommuteSummaryList(startMonth, endMonth);
    }

    @Override
    public CommuteDetailDTO getCommuteDetail(Long employeeId, String startDate, String endDate) {
        CommuteEmployeeInfoDTO employeeInfo = attendanceMapper.getEmployeeInfoById(employeeId);
        List<MyCommuteQueryDTO> commuteList = attendanceMapper.getCommuteDetailByIdAndDate(employeeId, startDate, endDate);

        CommuteDetailDTO result = new CommuteDetailDTO();
        result.setEmployeeInfo(employeeInfo);
        result.setCommuteList(commuteList);
        return result;
    }

    @Override
    public List<MyCommuteCorrectionQueryDTO> getCorrectionHistory(Long employeeId) {
        return attendanceMapper.findCommuteCorrectionsByEmployeeId(employeeId);
    }

    @Override
    public List<MyCommuteCorrectionQueryDTO> getCorrectionRequestHistory(Long employeeId) {
        return attendanceMapper.findCommuteCorrectionsRequestByEmployeeId(employeeId);
    }

    @Override
    public List<AllCommuteCorrectionQueryDTO> getAllCorrectionHistory() {
        return attendanceMapper.findAllCommuteCorrections();
    }

    @Override
    public List<AllCommuteCorrectionQueryDTO> getAllCorrectionRequestHistory() {
        return attendanceMapper.findAllCommuteCorrectionsRequest();
    }

}
