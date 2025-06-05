package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.AppointmentRequestDTO;
import com.ddis.ddis_hr.organization.command.application.dto.AppointmentResponseDTO;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.AppointmentEntity;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointment);
    AppointmentResponseDTO updateAppointment(Long appointmentId, AppointmentRequestDTO dto);
    void deleteAppointment(Long appointmentId);
}
