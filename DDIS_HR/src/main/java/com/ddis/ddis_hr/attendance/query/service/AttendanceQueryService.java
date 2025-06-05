package com.ddis.ddis_hr.attendance.query.service;

import com.ddis.ddis_hr.attendance.query.dao.AttendanceMapper;
import com.ddis.ddis_hr.attendance.query.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceQueryService {

    private final AttendanceMapper calendarMapper;

    public EmployeeInfoQueryDTO findById(Long employeeId) {
        return Optional.ofNullable(calendarMapper.findById(employeeId))
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));
    }

    public List<PersonalCalendarQueryDTO> getMyCalendar(Long employeeId) {
        EmployeeInfoQueryDTO employee = calendarMapper.findById(employeeId);
        Long teamId = employee.getTeamId();

        List<PersonalCalendarQueryDTO> events = new ArrayList<>();

        // 개인 일정
        for (PersonalScheduleQueryDTO ps : calendarMapper.findPersonalSchedules(employeeId)) {
            events.add(new PersonalCalendarQueryDTO("personal", ps.getScheduleTitle(), ps.getScheduleTime(), null, null, ps.getScheduleDate()));
        }

        // 근무 상태 일정 (본인 + 같은 팀)
        List<String> validStatuses = List.of("LATE", "ABSENT", "BUSINESS_TRIP", "FIELD_WORK", "ANNUAL_LEAVE", "HALF_AM", "HALF_PM");
        for (AttendanceQueryDTO at : calendarMapper.findWorkStatuses(teamId, employeeId, validStatuses)) {
            events.add(new PersonalCalendarQueryDTO("attendance", null, null, at.getWorkStatusName(), at.getEmployeeName(), at.getWorkDate()));
        }

        return events;
    }

    public List<TeamCalendarQueryDTO> getTeamCalendar(Long employeeId) {
        EmployeeInfoQueryDTO employee = calendarMapper.findById(employeeId);
        Long teamId = employee.getTeamId();

        List<TeamCalendarQueryDTO> events = new ArrayList<>();

        // 회의 일정
        for (MeetingQueryDTO m : calendarMapper.findMeetings(teamId)) {
            events.add(new TeamCalendarQueryDTO("meeting", m.getMeetingTitle(), m.getMeetingTime(), null, null, m.getMeetingDate()));
        }

        // 근무 상태 일정 (본인 + 같은 팀)
        List<String> validStatuses = List.of("LATE", "ABSENT", "BUSINESS_TRIP", "FIELD_WORK", "ANNUAL_LEAVE", "HALF_AM", "HALF_PM");
        for (AttendanceQueryDTO at : calendarMapper.findWorkStatuses(teamId, employeeId, validStatuses)) {
            events.add(new TeamCalendarQueryDTO("attendance", null, null, at.getWorkStatusName(), at.getEmployeeName(), at.getWorkDate()));
        }

        return events;
    }

}
