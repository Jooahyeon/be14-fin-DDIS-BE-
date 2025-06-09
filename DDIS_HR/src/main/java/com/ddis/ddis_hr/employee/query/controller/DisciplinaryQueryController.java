package com.ddis.ddis_hr.employee.query.controller;

import com.ddis.ddis_hr.employee.query.dto.MyDisciplinaryDTO;
import com.ddis.ddis_hr.employee.query.service.DisciplinaryQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/disciplinary")
public class DisciplinaryQueryController {

    private final DisciplinaryQueryService disciplinaryQueryService;

    @Autowired
    public DisciplinaryQueryController(DisciplinaryQueryService disciplinaryQueryService) {
        this.disciplinaryQueryService = disciplinaryQueryService;
    }


    // 내 징계 전체 조회
    @GetMapping("/my")
    public ResponseEntity<List<MyDisciplinaryDTO>> getMyDisciplinary(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long empId = user.getEmployeeId();
        List<MyDisciplinaryDTO> disciplinary = disciplinaryQueryService.findByEmployeeId(empId);
        return ResponseEntity.ok(disciplinary);
    }
}
