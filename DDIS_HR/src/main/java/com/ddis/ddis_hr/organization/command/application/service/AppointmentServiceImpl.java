package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.AppointmentRequestDTO;
import com.ddis.ddis_hr.organization.command.application.dto.AppointmentResponseDTO;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.AppointmentEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {

        AppointmentEntity entity = modelMapper.map(dto, AppointmentEntity.class);

        entity.setAppointmentCreatedAt(LocalDate.now());

        AppointmentEntity saved = appointmentRepository.save(entity);

        AppointmentResponseDTO response = modelMapper.map(saved, AppointmentResponseDTO.class);

        return response;
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long appointmentId, AppointmentRequestDTO dto) {
        AppointmentEntity existing = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + appointmentId));

        modelMapper.map(existing, dto);

        AppointmentEntity saved = appointmentRepository.save(existing);

        AppointmentResponseDTO response = modelMapper.map(saved, AppointmentResponseDTO.class);

        return response;
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        AppointmentEntity existing = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + appointmentId));
        appointmentRepository.delete(existing);
    }
}
