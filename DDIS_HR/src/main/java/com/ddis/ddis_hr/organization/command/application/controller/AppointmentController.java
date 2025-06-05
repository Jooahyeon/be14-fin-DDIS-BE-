package com.ddis.ddis_hr.organization.command.application.controller;

import com.ddis.ddis_hr.organization.command.application.dto.AppointmentRequestDTO;
import com.ddis.ddis_hr.organization.command.application.dto.AppointmentResponseDTO;
import com.ddis.ddis_hr.organization.command.application.service.AppointmentService;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.AppointmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /** 등록(Create) **/
    @PostMapping("/create")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO requestDTO) {
        AppointmentResponseDTO created = appointmentService.createAppointment(requestDTO);
        return ResponseEntity.ok(created);
    }

    /** 수정(Update) **/
    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequestDTO requestDTO) {
        AppointmentResponseDTO updated = appointmentService.updateAppointment(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    /** 삭제(Delete) **/
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
