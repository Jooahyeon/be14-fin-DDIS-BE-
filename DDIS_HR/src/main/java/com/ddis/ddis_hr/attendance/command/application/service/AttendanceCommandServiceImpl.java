package com.ddis.ddis_hr.attendance.command.application.service;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.Attendance;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.WorkStatus;
import com.ddis.ddis_hr.attendance.command.domain.repository.AttendanceRepository;
import com.ddis.ddis_hr.attendance.command.domain.repository.WorkStatusRepository;
import com.ddis.ddis_hr.attendance.command.domain.repository.AttendanceEmployeeRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AttendanceCommandServiceImpl implements AttendanceCommandService{

    private final AttendanceRepository attendanceRepository;
    private final AttendanceEmployeeRepository employeeRepository;
    private final WorkStatusRepository workStatusRepository;

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
}
