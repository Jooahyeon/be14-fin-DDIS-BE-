package com.ddis.ddis_hr.attendance.command.domain.aggregate;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "leave_alert_log")
public class LeaveAlertLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_alert_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "alert_type", nullable = false)
    private String alertType;

    @Column(name = "send_datetime", nullable = false)
    private LocalDateTime sendDatetime;

    public LeaveAlertLog() {}

    public LeaveAlertLog(Employee employee, String alertType, LocalDateTime sendDatetime) {
        this.employee = employee;
        this.alertType = alertType;
        this.sendDatetime = sendDatetime;
    }

    // getter/setter ...
}

