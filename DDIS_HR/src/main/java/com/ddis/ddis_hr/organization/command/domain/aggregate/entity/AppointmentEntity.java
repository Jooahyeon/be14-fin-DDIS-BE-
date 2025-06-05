package com.ddis.ddis_hr.organization.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "appointment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "from_head_code")
    private String fromHeadCode;

    @Column(name = "to_head_code")
    private String toHeadCode;

    @Column(name = "from_department_code")
    private String fromDepartmentCode;

    @Column(name = "to_department_code")
    private String toDepartmentCode;

    @Column(name = "from_team_code")
    private String fromTeamCode;

    @Column(name = "to_team_code")
    private String toTeamCode;

    @Column(name = "from_position_code")
    private String fromPositionCode;

    @Column(name = "to_position_code")
    private String toPositionCode;

    @Column(name = "from_rank_code")
    private String fromRankCode;

    @Column(name = "to_rank_code")
    private String toRankCode;

    @Column(name = "from_job_code")
    private String fromJobCode;

    @Column(name = "to_job_code")
    private String toJobCode;

    @Column(name = "appointment_type")
    private String appointmentType;

    @Column(name = "appointment_reason")
    private String appointmentReason;

    @Column(name = "appointment_created_at", nullable = false)
    private LocalDate appointmentCreatedAt;

    @Column(name = "appointment_effective_date", nullable = false)
    private LocalDate appointmentEffectiveDate;

    @Column(name = "appointment_status")
    private String appointmentStatus;

    @Column(name = "is_applied")
    private Boolean isApplied = false;
}
