package com.ddis.ddis_hr.attendance.command.application.service;

import com.ddis.ddis_hr.attendance.command.application.dto.AttendanceCorrectionRequestDTO;
import com.ddis.ddis_hr.attendance.command.application.dto.MeetingScheduleRequestDTO;
import com.ddis.ddis_hr.attendance.command.application.dto.PersonalScheduleRequestDTO;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.Attendance;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.Meeting;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.PersonalSchedule;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.WorkStatus;
import com.ddis.ddis_hr.attendance.command.domain.repository.*;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.TeamEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AttendanceCommandServiceImpl implements AttendanceCommandService{

    private final AttendanceRepository attendanceRepository;
    private final AttendanceEmployeeRepository employeeRepository;
    private final WorkStatusRepository workStatusRepository;
    private final PersonalScheduleRepository repository;
    private final MeetingScheduleRepository meetingScheduleRepository;
    private final TeamRepository teamRepository;

    @Override
    public void checkIn(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다."));

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        if (attendanceRepository.existsByEmployeeAndWorkDate(employee, today)) {
            throw new IllegalStateException("이미 출근한 상태입니다.");
        }

        String statusId;
        if (now.isBefore(LocalTime.of(9, 0))) {
            statusId = "NORMAL";
        } else if (now.isBefore(LocalTime.of(12, 0))) {
            statusId = "LATE";
        } else {
            throw new IllegalStateException("이미 결근 처리 시간이 지났습니다.");
        }

        WorkStatus workStatus = workStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalStateException("근무 상태 정보가 없습니다."));

        Attendance attendance = new Attendance(
                employee,
                today,
                now,
                workStatus
        );

        attendanceRepository.save(attendance);
    }

    @Override
    public void checkOut(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다."));

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByEmployeeAndWorkDate(employee, today)
                .orElseThrow(() -> new IllegalStateException("출근 기록이 없습니다."));

        if (attendance.getCheckOutTime() != null) {
            throw new IllegalStateException("이미 퇴근 처리된 기록입니다.");
        }

        attendance.updateCheckOutTime(LocalTime.now());
        attendanceRepository.save(attendance);
    }

    @Override
    public void personalScheduleRegister(PersonalScheduleRequestDTO dto, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원"));

        PersonalSchedule schedule = PersonalSchedule.builder()
                .employee(employee)
                .scheduleDate(LocalDate.parse(dto.getScheduleDate()))
                .scheduleTitle(dto.getScheduleTitle())
                .scheduleTime(dto.getScheduleTime())
                .build();

        repository.save(schedule);
    }

    @Override
    public void MeetingScheduleRegister(MeetingScheduleRequestDTO dto, Long employeeId, Long teamId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원"));
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀"));

        Meeting meeting = Meeting.builder()
                .employee(employee)
                .team(team)
                .meetingDate(LocalDate.parse(dto.getMeetingDate()))
                .meetingTitle(dto.getMeetingTitle())
                .meetingTime(dto.getMeetingTime())
                .build();

        meetingScheduleRepository.save(meeting);
    }

    @Override
    @Transactional
    public void requestCorrection(Long employeeId, AttendanceCorrectionRequestDTO dto) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByEmployee_EmployeeIdAndWorkDate(employeeId, today)
                .orElseThrow(() -> new IllegalStateException("출근 기록이 없습니다. 먼저 출근해주세요."));

        attendance.applyCorrection(
                LocalTime.parse(dto.getRequestedTimeChange()),
                dto.getReason()
        );
    }

}
