package com.ddis.ddis_hr.attendance.command.application.controller;

import com.ddis.ddis_hr.attendance.command.application.service.AttendanceCommandService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceCommandController {

    private final AttendanceCommandService checkInService;

    @PostMapping("/check-in")
    public ResponseEntity<String> checkIn(@AuthenticationPrincipal CustomUserDetails user) {
        checkInService.checkIn(user.getEmployeeId());
        return ResponseEntity.ok("출근이 정상적으로 등록되었습니다.");
    }

    @PutMapping("/check-out")
    public ResponseEntity<String> checkOut(@AuthenticationPrincipal CustomUserDetails user) {
        checkInService.checkOut(user.getEmployeeId());
        return ResponseEntity.ok("퇴근이 정상적으로 등록되었습니다.");
    }

}
