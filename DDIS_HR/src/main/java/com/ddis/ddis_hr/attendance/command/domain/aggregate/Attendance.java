package com.ddis.ddis_hr.attendance.command.domain.aggregate;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@Getter
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_status_id", nullable = false)
    private WorkStatus workStatus;

    @Column(nullable = false)
    private LocalDate workDate;

    @Column
    private LocalTime checkInTime;

    @Column
    private LocalTime checkOutTime;

    @Column
    private Integer workDuration; // 분 또는 시간 단위 선택 (예: 480 = 8시간)

    @Column(nullable = false)
    private String overtimeType = "시간외근무"; // 시간외근무, 야간근무, 휴일근무

    @Column
    private Integer overtimeDuration; // 분 또는 시간 단위

    @Column
    private LocalDateTime requestTime;

    @Column
    private LocalDateTime requestedTimeChange;

    @Column(nullable = false)
    private String approvalStatus = "대기중"; // 승인, 대기중, 반려

    @Column
    private LocalDateTime processedTime;

    @Column
    private String reason;

    @Column
    private String rejectReason;

    // 필요 시 생성자, setter 또는 update 메서드 추가
}