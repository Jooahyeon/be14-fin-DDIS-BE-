package com.ddis.ddis_hr.attendance.command.domain.aggregate;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "`leave`")
public class Leave {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "total_days", nullable = false)
    private Integer totalDays = 0;

    @Column(name = "used_days", nullable = false, precision = 3, scale = 1)
    private BigDecimal usedDays = BigDecimal.ZERO;

    @Column(name = "remaining_days", nullable = false, precision = 3, scale = 1)
    private BigDecimal remainingDays = BigDecimal.ZERO;

    @Column(name = "pending_leave_days", nullable = false)
    private Integer pendingLeaveDays = 0;

    @Column(name = "first_notice_date")
    private LocalDate firstNoticeDate;

    @Column(name = "second_notice_date")
    private LocalDate secondNoticeDate;

    // 생성자
    public Leave() {}

    public Leave(Long employeeId) {
        this.employeeId = employeeId;
        this.totalDays = 15;
        this.usedDays = BigDecimal.ZERO;
        this.remainingDays = BigDecimal.valueOf(15.0);
        this.pendingLeaveDays = 0;
        this.firstNoticeDate = null;
        this.secondNoticeDate = null;
    }

}
