package com.ddis.ddis_hr.organization.command.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentResponseDTO {
    private Long appointmentId;
    private Long employeeId;
    private String fromHeadCode;
    private String toHeadCode;
    private String fromDepartmentCode;
    private String toDepartmentCode;
    private String fromTeamCode;
    private String toTeamCode;
    private String fromPositionCode;
    private String toPositionCode;
    private String fromRankCode;
    private String toRankCode;
    private String fromJobCode;
    private String toJobCode;
    private String appointmentType;
    private String appointmentReason;
    private LocalDate appointmentCreatedAt;
    private LocalDate appointmentEffectiveDate;
    private String appointmentStatus;
    private Boolean isApplied;


}
