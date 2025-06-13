package com.ddis.ddis_hr.attendance.command.application.scheduler;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.Attendance;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.WorkStatus;
import com.ddis.ddis_hr.attendance.command.domain.repository.AttendanceRepository;
import com.ddis.ddis_hr.attendance.command.domain.repository.WorkStatusRepository;
import com.ddis.ddis_hr.attendance.command.domain.repository.AttendanceEmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class AbsenceScheduler {

    private final AttendanceEmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final WorkStatusRepository workStatusRepository;

    /**
     * 매일 정오 12시에 결근 처리 스케줄러
     */
    @Scheduled(cron = "0 0 12 * * *", zone = "Asia/Seoul") // 매일 12:00:00
    public void markAbsentIfNoCheckIn() {
        LocalDate today = LocalDate.now();

        WorkStatus absentStatus = workStatusRepository.findById("ABSENT")
                .orElseThrow(() -> new IllegalStateException("ABSENT 상태가 존재하지 않습니다."));

        employeeRepository.findAll().forEach(employee -> {
            boolean alreadyExists = attendanceRepository.existsByEmployeeAndWorkDate(employee, today);
            if (!alreadyExists) {
                Attendance absent = new Attendance(
                        employee,
                        today,
                        null,               // 출근 시간 없음
                        absentStatus,
                        null                // beforeCheckInTime 없음
                );
                attendanceRepository.save(absent);
                log.info("[결근 처리] {} ({})", employee.getEmployeeName(), employee.getEmployeeId());
            }
        });
    }
}
