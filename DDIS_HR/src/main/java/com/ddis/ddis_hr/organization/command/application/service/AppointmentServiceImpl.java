package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.employee.command.domain.repository.EmployeesRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.organization.command.application.dto.AppointmentRequestDTO;
import com.ddis.ddis_hr.organization.command.application.dto.AppointmentResponseDTO;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.AppointmentEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final EmployeesRepository employeesRepository;

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {

        AppointmentEntity entity = modelMapper.map(dto, AppointmentEntity.class);

        entity.setAppointmentCreatedAt(LocalDate.now());

        Employee employee = employeesRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "사원(employee_id=" + dto.getEmployeeId() + ")을 찾을 수 없습니다."));
        entity.setEmployee(employee);

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
