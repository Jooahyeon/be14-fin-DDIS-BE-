package com.ddis.ddis_hr.organization.query.service;

import com.ddis.ddis_hr.organization.query.dao.AppointmentMapper;
import com.ddis.ddis_hr.organization.query.dto.AppointmentQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentQueryService {

    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentQueryService(AppointmentMapper appointmentMapper) {
        this.appointmentMapper = appointmentMapper;
    }

    public List<AppointmentQueryDTO> findAll() {
        return appointmentMapper.selectAll();
    }

    public AppointmentQueryDTO findById(Long appointmentId) {
        return appointmentMapper.selectByAppointmentId(appointmentId);
    }

    public List<AppointmentQueryDTO> findByEmployeeId(Long employeeId) {
        return appointmentMapper.selectByEmployeeId(employeeId);
    }

    public List<AppointmentQueryDTO> findByStatus(String status) {
        return appointmentMapper.selectByStatus(status);
    }
}
