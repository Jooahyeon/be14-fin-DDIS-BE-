package com.ddis.ddis_hr.attendance.query.controller;

import com.ddis.ddis_hr.attendance.query.dto.PersonalCalendarQueryDTO;
import com.ddis.ddis_hr.attendance.query.dto.TeamCalendarQueryDTO;
import com.ddis.ddis_hr.attendance.query.service.AttendanceQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceQueryController {

    private final AttendanceQueryService personalCalendarQueryService;

    @GetMapping("/calendar/personal")
    public List<PersonalCalendarQueryDTO> getMyCalendar(@AuthenticationPrincipal CustomUserDetails user) {
        return personalCalendarQueryService.getMyCalendar(user.getEmployeeId());
    }

    @GetMapping("/calendar/team")
    public List<TeamCalendarQueryDTO> getTeamCalendar(@AuthenticationPrincipal CustomUserDetails user) {
        return personalCalendarQueryService.getTeamCalendar(user.getEmployeeId());
    }
}
