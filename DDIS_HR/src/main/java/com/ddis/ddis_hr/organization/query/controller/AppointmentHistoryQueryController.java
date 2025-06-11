package com.ddis.ddis_hr.organization.query.controller;

import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.organization.query.dto.AppointmentHistoryQueryDTO;
import com.ddis.ddis_hr.organization.query.service.AppointmentHistoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment-history")
public class AppointmentHistoryQueryController {

    private final AppointmentHistoryQueryService historyQueryService;

    @Autowired
    public AppointmentHistoryQueryController(AppointmentHistoryQueryService historyQueryService) {
        this.historyQueryService = historyQueryService;
    }

    /** 1) 모든 이력 조회 */
    @GetMapping("/all")
    public ResponseEntity<List<AppointmentHistoryQueryDTO>> getAllHistories() {
        List<AppointmentHistoryQueryDTO> list = historyQueryService.getAllHistories();
        return ResponseEntity.ok(list);
    }

    /** 2) 특정 사원(Employee) 이력 조회 */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AppointmentHistoryQueryDTO>> getByEmployeeId(
            @PathVariable("employeeId") Long employeeId) {
        List<AppointmentHistoryQueryDTO> list = historyQueryService.getHistoriesByEmployeeId(employeeId);
        return ResponseEntity.ok(list);
    }

    /** 3) 특정 appointmentId 이력 조회 */
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<AppointmentHistoryQueryDTO>> getByAppointmentId(
            @PathVariable("appointmentId") Long appointmentId) {
        List<AppointmentHistoryQueryDTO> list = historyQueryService.getHistoriesByAppointmentId(appointmentId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/history/{appointmentHistoryId}")
    public ResponseEntity<List<AppointmentHistoryQueryDTO>> getHistoriesByHistoryId(
            @PathVariable("appointmentHistoryId") Long appointmentHistoryId) {
        List<AppointmentHistoryQueryDTO> list = historyQueryService.getHistoriesByHistoryId(appointmentHistoryId);
        return ResponseEntity.ok(list);
    }

    /** ── appointment_type별 이력 조회 ── */
    @GetMapping("/type/{appointmentType}")
    public ResponseEntity<List<AppointmentHistoryQueryDTO>> getByAppointmentType(
            @PathVariable("appointmentType") String appointmentType) {
        List<AppointmentHistoryQueryDTO> list = historyQueryService.getByAppointmentType(appointmentType);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/approved")
    public ResponseEntity<List<AppointmentHistoryQueryDTO>> getApprovedOnly() {
        List<AppointmentHistoryQueryDTO> approved = historyQueryService.getApprovedHistories();
        return ResponseEntity.ok(approved);
    }
}
