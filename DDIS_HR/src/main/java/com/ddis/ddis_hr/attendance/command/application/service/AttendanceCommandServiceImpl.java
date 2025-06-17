package com.ddis.ddis_hr.attendance.command.application.service;

import com.ddis.ddis_hr.attendance.command.application.dto.AttendanceCorrectionRequestDTO;
import com.ddis.ddis_hr.attendance.command.application.dto.MeetingScheduleRequestDTO;
import com.ddis.ddis_hr.attendance.command.application.dto.OvertimeRequestDTO;
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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

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

        Optional<Attendance> optionalAttendance = attendanceRepository.findByEmployeeAndWorkDate(employee, today);

        if (optionalAttendance.isPresent()) {
            Attendance existing = optionalAttendance.get();

            if (existing.getCheckInTime() != null) {
                throw new IllegalStateException("이미 출근한 상태입니다.");
            }

            // ✅ 출근시간만 없는 경우: UPDATE
            existing.setCheckInTime(now);
            existing.setBeforeCheckInTime(now); // before 필드도 갱신
            attendanceRepository.save(existing);
            return;
        }

        // ✅ 완전히 새로운 경우: 근무 상태 판단해서 INSERT
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
                workStatus,
                now
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

        LocalTime checkOutTime = LocalTime.now();
        String workStatus = attendance.getWorkStatus().getId();

        LocalTime checkInTime = attendance.getCheckInTime();
        int workDuration = 0;

        LocalTime lunchStart = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);
        LocalTime standardLeaveTime = LocalTime.of(18, 0);

        switch (workStatus) {
            case "NORMAL":
                workDuration = 480;
                break;
            case "LATE":
            case "HALF_AM":
                if (checkInTime == null) {
                    throw new IllegalStateException("출근 시간이 없습니다.");
                }
                long minutes = Duration.between(checkInTime, standardLeaveTime).toMinutes();
                // 점심시간 감산 조건: 출근이 점심 전, 퇴근이 점심 후
                if (checkInTime.isBefore(lunchStart) && standardLeaveTime.isAfter(lunchEnd)) {
                    minutes -= 60;
                }
                workDuration = (int) Math.max(minutes, 0);
                break;
            case "HALF_PM":
                workDuration = 180;
                break;
            case "ABSENT":
                workDuration = 0;
                break;
            default:
                workDuration = 0; // 출장, 외근 등 기타 유형
        }
        attendance.updateCheckOutTime(checkOutTime);
        attendance.updateWorkDuration(workDuration);
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

    @Transactional
    @Override
    public void requestCorrection(Long employeeId, AttendanceCorrectionRequestDTO dto) {
        LocalDate targetDate = LocalDate.parse(dto.getRequestedDate());
        Attendance attendance = attendanceRepository.findByEmployee_EmployeeIdAndWorkDate(employeeId, targetDate)
                .orElseThrow(() -> new IllegalStateException("출근 기록이 없습니다. 먼저 출근해주세요."));

        attendance.applyCorrection(
                LocalTime.parse(dto.getRequestedTimeChange()),
                dto.getReason()
        );
    }

    @Transactional
    @Override
    public void approveCorrection(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new IllegalArgumentException("출근 기록을 찾을 수 없습니다."));

        attendance.setApprovalStatus("승인");
        attendance.setProcessedTime(LocalDateTime.now());

        LocalTime correctedTime = attendance.getRequestedTimeChange().toLocalTime();
        attendance.setCheckInTime(correctedTime);

        if (correctedTime.isBefore(LocalTime.of(9, 0))) {
            WorkStatus normal = workStatusRepository.findById("NORMAL")
                    .orElseThrow(() -> new IllegalArgumentException("정상근무 상태가 없습니다."));
            attendance.setWorkStatus(normal);

        } else if (correctedTime.isBefore(LocalTime.of(12, 0))) {
            WorkStatus late = workStatusRepository.findById("LATE")
                    .orElseThrow(() -> new IllegalArgumentException("지각 상태가 없습니다."));
            attendance.setWorkStatus(late);
        }
    }


    @Transactional
    @Override
    public void rejectCorrection(Long attendanceId, String rejectReason) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new IllegalArgumentException("출근 기록을 찾을 수 없습니다."));

        attendance.setApprovalStatus("반려");
        attendance.setProcessedTime(LocalDateTime.now());
        attendance.setRejectReason(rejectReason);
    }

    @Transactional
    @Override
    public void handleOvertimeRequest(OvertimeRequestDTO dto, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("사원이 존재하지 않습니다."));

        LocalDate targetDate = dto.getDate() != null
                ? LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : LocalDate.now();

        LocalDate startOfWeek = targetDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        List<Attendance> weeklyAttendance = attendanceRepository.findByEmployee_EmployeeIdAndWorkDateBetween(employeeId, startOfWeek, endOfWeek);

        int totalOvertimeMinutes = weeklyAttendance.stream()
                .mapToInt(a -> a.getOvertimeDuration() != null ? a.getOvertimeDuration() : 0)
                .sum();

        int requestedMinutes = dto.getDuration() != null ? dto.getDuration() * 60 : 300; // 300 = 5시간

        System.out.println("이번 주 범위: " + startOfWeek + " ~ " + endOfWeek);

        System.out.println("이번주 누적 초과근무: " + totalOvertimeMinutes + "분");
        System.out.println("신청 요청 시간: " + requestedMinutes + "분");

        System.out.println("이번 주 출근 기록 수: " + weeklyAttendance.size());
        weeklyAttendance.forEach(a -> {
            System.out.println("날짜: " + a.getWorkDate() +
                    ", OT: " + a.getOvertimeDuration());
        });

        if (totalOvertimeMinutes + requestedMinutes > 720) {
            throw new IllegalArgumentException("이번 주 초과 근무 시간은 최대 12시간입니다.");
        }

        if ("휴일근무".equals(dto.getType())) {
            WorkStatus normalStatus = workStatusRepository.findById("NORMAL")
                    .orElseThrow(() -> new IllegalArgumentException("정상근무 상태(NORMAL)가 존재하지 않습니다."));

            Attendance newAttendance = new Attendance(
                    employee,
                    targetDate,
                    LocalTime.of(10, 0),
                    normalStatus,
                    LocalTime.of(10, 0)
            );
            newAttendance.setCheckOutTime(LocalTime.of(16, 0));
            newAttendance.setOvertimeType("휴일근무");
            newAttendance.setOvertimeDuration(300);

            attendanceRepository.save(newAttendance);
        } else {
            Attendance todayAttendance = attendanceRepository.findByEmployee_EmployeeIdAndWorkDate(employeeId, targetDate)
                    .orElseThrow(() -> new IllegalArgumentException("해당 일자의 출근 기록이 없습니다."));

            todayAttendance.setOvertimeType(dto.getType());
            todayAttendance.setOvertimeDuration(requestedMinutes);

            attendanceRepository.save(todayAttendance);
        }
    }



}
