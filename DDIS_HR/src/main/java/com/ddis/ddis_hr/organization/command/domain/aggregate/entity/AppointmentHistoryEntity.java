package com.ddis.ddis_hr.organization.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "appointment_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_history_id")
    private Long appointmentHistoryId;

    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "employee_id")
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

    @Column(name = "appointment_effective_date")
    private LocalDate appointmentEffectiveDate;

    @Column(name = "appointment_history_created_at")
    private LocalDate appointmentHistoryCreatedAt;
}
