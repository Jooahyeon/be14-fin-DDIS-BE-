package com.ddis.ddis_hr.attendance.query.service;

import com.ddis.ddis_hr.attendance.query.dao.AttendanceMapper;
import com.ddis.ddis_hr.attendance.query.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceQueryServiceImpl implements AttendanceQueryService {

    private final AttendanceMapper calendarMapper;

    @Override
    public EmployeeInfoQueryDTO findById(Long employeeId) {
        return Optional.ofNullable(calendarMapper.findById(employeeId))
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));
    }

    @Override
    public List<PersonalCalendarQueryDTO> getMyCalendar(Long employeeId) {
        EmployeeInfoQueryDTO employee = findById(employeeId);
        Long teamId = employee.getTeamId();

        List<PersonalCalendarQueryDTO> events = new ArrayList<>();

        for (PersonalScheduleQueryDTO ps : calendarMapper.findPersonalSchedules(employeeId)) {
            events.add(new PersonalCalendarQueryDTO("personal", ps.getScheduleTitle(), ps.getScheduleTime(), null, null, ps.getScheduleDate()));
        }

        List<String> validStatuses = List.of("LATE", "ABSENT", "BUSINESS_TRIP", "FIELD_WORK", "ANNUAL_LEAVE", "HALF_AM", "HALF_PM");
        for (AttendanceQueryDTO at : calendarMapper.findWorkStatuses(teamId, employeeId, validStatuses)) {
            events.add(new PersonalCalendarQueryDTO("attendance", null, null, at.getWorkStatusName(), at.getEmployeeName(), at.getWorkDate()));
        }

        return events;
    }

    @Override
    public List<TeamCalendarQueryDTO> getTeamCalendar(Long employeeId) {
        EmployeeInfoQueryDTO employee = findById(employeeId);
        Long teamId = employee.getTeamId();

        List<TeamCalendarQueryDTO> events = new ArrayList<>();

        for (MeetingQueryDTO m : calendarMapper.findMeetings(teamId)) {
            events.add(new TeamCalendarQueryDTO("meeting", m.getMeetingTitle(), m.getMeetingTime(), null, null, m.getMeetingDate()));
        }

        List<String> validStatuses = List.of("LATE", "ABSENT", "BUSINESS_TRIP", "FIELD_WORK", "ANNUAL_LEAVE", "HALF_AM", "HALF_PM");
        for (AttendanceQueryDTO at : calendarMapper.findWorkStatuses(teamId, employeeId, validStatuses)) {
            events.add(new TeamCalendarQueryDTO("attendance", null, null, at.getWorkStatusName(), at.getEmployeeName(), at.getWorkDate()));
        }

        return events;
    }

    @Override
    public List<MeetingQueryDTO> getTodayMeetings(Long employeeId) {
        EmployeeInfoQueryDTO employee = calendarMapper.findById(employeeId);
        Long teamId = employee.getTeamId();
        return calendarMapper.findMeetingsToday(teamId, LocalDate.now());
    }

    @Override
    public List<TeamWorkStatusQueryDTO> getTeamWorkStatus(Long employeeId) {
        EmployeeInfoQueryDTO employee = calendarMapper.findById(employeeId);
        Long teamId = employee.getTeamId();
        String today = LocalDate.now().toString();
        return calendarMapper.findTodayTeamStatuses(teamId, today);
    }

    @Override
    public String getTeamName(Long teamId) {
        return calendarMapper.findTeamNameById(teamId);
    }

}
