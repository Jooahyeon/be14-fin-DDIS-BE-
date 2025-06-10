package com.ddis.ddis_hr.attendance.command.domain.aggregate;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalScheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate scheduleDate;
    private String scheduleTitle;
    private String scheduleTime;
}