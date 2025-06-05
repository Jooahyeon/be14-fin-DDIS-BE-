package com.ddis.ddis_hr.attendance.query.controller;

import com.ddis.ddis_hr.attendance.query.dto.PersonalCalendarQueryDTO;
import com.ddis.ddis_hr.attendance.query.dto.TeamCalendarQueryDTO;
import com.ddis.ddis_hr.attendance.query.service.AttendanceQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceQueryController {

    private final AttendanceQueryService attendanceQueryService;

    @GetMapping("/calendar/personal")
    public ResponseEntity<List<PersonalCalendarQueryDTO>> getMyCalendar(@AuthenticationPrincipal CustomUserDetails user) {
        List<PersonalCalendarQueryDTO> result = attendanceQueryService.getMyCalendar(user.getEmployeeId());
        return ResponseEntity.ok(result); // HTTP 200 OK
    }

    @GetMapping("/calendar/team")
    public ResponseEntity<List<TeamCalendarQueryDTO>> getTeamCalendar(@AuthenticationPrincipal CustomUserDetails user) {
        List<TeamCalendarQueryDTO> result = attendanceQueryService.getTeamCalendar(user.getEmployeeId());
        return ResponseEntity.ok(result); // HTTP 200 OK
    }
}
