package com.ddis.ddis_hr.attendance.command.application.scheduler;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.Attendance;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.Leave;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.LeaveAlertLog;
import com.ddis.ddis_hr.attendance.command.domain.aggregate.WorkStatus;
import com.ddis.ddis_hr.attendance.command.domain.repository.*;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.notice.command.application.event.NoticeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AttendanceScheduler {

    private final AttendanceEmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final WorkStatusRepository workStatusRepository;
    private final LeaveRepository leaveRepository;
    private final LeaveAlertLogRepository leaveAlertLogRepository;
    private final ApplicationEventPublisher publisher;

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

    /**
     * 매일 새벽 03시에 연차 지급/촉진 스케줄러
     */
    @Transactional
    @Scheduled(cron = "0 53 10 * * *", zone = "Asia/Seoul") // 매일 오전 03:00 실행
    public void handleAnnualLeaveAndPromotion() {
        LocalDate today = LocalDate.now();
        List<Employee> employeeList = employeeRepository.findAll();

        for (Employee emp : employeeList) {
            if (emp.getRetirementDate() != null && emp.getRetirementDate().isBefore(today)) continue;

            Long empId = emp.getEmployeeId();
            LocalDate joinDate = emp.getEmploymentDate();
            LocalDate oneYearLater = joinDate.plusYears(1);
            MonthDay todayMMDD = MonthDay.from(today);
            MonthDay joinMMDD = MonthDay.from(joinDate);

            // 🟢 Leave 엔티티 조회 or 생성
            Leave leave = leaveRepository.findById(empId).orElse(null);
            boolean isNew = false;

            if (leave == null) {
                leave = new Leave();
                leave.setEmployee(emp); // @MapsId 관계로 employee_id 지정됨
                leave.setTotalDays(0);
                leave.setUsedDays(BigDecimal.ZERO);
                leave.setRemainingDays(BigDecimal.ZERO);
                leave.setPendingLeaveDays(0);
                isNew = true;
            }

            // ✅ 1. 입사 1년차 당일: 15일 초기화 지급
            if (todayMMDD.equals(joinMMDD)) {
                leave.setTotalDays(15);
                leave.setUsedDays(BigDecimal.ZERO);
                leave.setRemainingDays(BigDecimal.valueOf(15.0));
                leave.setPendingLeaveDays(0);
                leave.setFirstNoticeDate(null);
                leave.setSecondNoticeDate(null);

                leaveRepository.save(leave);
                log.info("[연차 지급] {} ({}): 1년차 15일 지급 완료", emp.getEmployeeName(), empId);
                continue; // 이후 처리 생략
            }

            // ✅ 2. 입사 1년 미만 + 매월 1일이면 1일씩 지급
            if (today.isBefore(oneYearLater) && today.getDayOfMonth() == 1) {
                leave.setTotalDays(leave.getTotalDays() + 1);
                leave.setRemainingDays(leave.getRemainingDays().add(BigDecimal.valueOf(1.0)));
                log.info("[연차 지급] {} ({}): 월 1일 지급", emp.getEmployeeName(), empId);
            }

            // ✅ 3. 연차 촉진 (남은 연차 있을 때만)
            if (leave.getRemainingDays().compareTo(BigDecimal.ZERO) > 0) {
                // 매년의 입사기념일에서 6개월 전, 2개월 전 기준으로 계산
                LocalDate thisYearsJoinAnniversary = LocalDate.of(today.getYear(), joinDate.getMonth(), joinDate.getDayOfMonth());
                LocalDate firstPromotionDate = thisYearsJoinAnniversary.minusMonths(6);
                LocalDate secondPromotionDate = thisYearsJoinAnniversary.minusMonths(2);

                if (today.isEqual(firstPromotionDate) && leave.getFirstNoticeDate() == null) {
                    leave.setFirstNoticeDate(today);
                    leaveAlertLogRepository.save(new LeaveAlertLog(emp, "FIRST", LocalDateTime.now()));
                    log.info("[1차 촉진] {} ({})", emp.getEmployeeName(), empId);

                    // 1차 연차 촉진 알림
                    publisher.publishEvent(new NoticeEvent(
                            this,
                            "연차촉진",
                            "1차 연차 촉진 안내",
                            "연차 소멸 6개월 전입니다.",
                            Collections.singletonList(empId)
                    ));
                }

                if (today.isEqual(secondPromotionDate) && leave.getSecondNoticeDate() == null) {
                    leave.setSecondNoticeDate(today);
                    leaveAlertLogRepository.save(new LeaveAlertLog(emp, "SECOND", LocalDateTime.now()));
                    log.info("[2차 촉진] {} ({})", emp.getEmployeeName(), empId);

                    // 2차 연차 촉진 알림
                    publisher.publishEvent(new NoticeEvent(
                            this,
                            "연차촉진",
                            "2차 연차 촉진 안내",
                            "연차 소멸 2개월 전입니다.",
                            Collections.singletonList(empId)
                    ));
                }
            }

            // ✅ 저장 (신규 생성이든 수정이든)
            leaveRepository.save(leave);
        }

        log.info("✅ 연차 지급 및 촉진 스케줄러 완료: {}", today);
    }

}
