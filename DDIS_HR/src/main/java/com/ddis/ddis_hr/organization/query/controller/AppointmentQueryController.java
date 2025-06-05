package com.ddis.ddis_hr.organization.query.controller;

import com.ddis.ddis_hr.organization.query.dto.AppointmentQueryDTO;
import com.ddis.ddis_hr.organization.query.service.AppointmentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentQueryController {

    private final AppointmentQueryService appointmentQueryService;

    @Autowired
    public AppointmentQueryController(AppointmentQueryService appointmentQueryService) {
        this.appointmentQueryService = appointmentQueryService;
    }

    // 모든 appointment 조회
    @GetMapping("/all")
    public ResponseEntity<List<AppointmentQueryDTO>> getAllAppointments() {
        List<AppointmentQueryDTO> list = appointmentQueryService.findAll();
        return ResponseEntity.ok(list);
    }

    // appointment_id 로 단건 조회
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentQueryDTO> getAppointmentById(
            @PathVariable("appointmentId") Long appointmentId
    ) {
        AppointmentQueryDTO dto = appointmentQueryService.findById(appointmentId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * GET /appointment/{employeeId}
     * employee_id 로 해당 사원의 appointment 목록 조회
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AppointmentQueryDTO>> getByEmployeeId(
            @PathVariable("employeeId") Long employeeId
    ) {
        List<AppointmentQueryDTO> list = appointmentQueryService.findByEmployeeId(employeeId);
        return ResponseEntity.ok(list);
    }

    /**
     * GET /appointment/status/{status}
     * appointment_status 로 조회 (예: "승인", "대기" 등)
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentQueryDTO>> getByStatus(
            @PathVariable("status") String status
    ) {
        List<AppointmentQueryDTO> list = appointmentQueryService.findByStatus(status);
        return ResponseEntity.ok(list);
    }
}
