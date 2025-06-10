package com.ddis.ddis_hr.employee.query.controller;

import com.ddis.ddis_hr.employee.query.dto.MyDisciplinaryDTO;
import com.ddis.ddis_hr.employee.query.service.DisciplinaryQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/disciplinary")
public class DisciplinaryQueryController {

    private final DisciplinaryQueryService service;

    @Autowired
    public DisciplinaryQueryController(DisciplinaryQueryService service) {
        this.service = service;
    }

    /**
     * 1) 내 징계 목록 조회
     *    - ROLE_USER, ROLE_HR 모두 호출 가능
     *    - 본인 것만 반환
     *    - GET /disciplinary/my
     */
    @GetMapping("/my")
    public ResponseEntity<List<MyDisciplinaryDTO>> getMyDisciplinary(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                service.findByEmployeeId(user.getEmployeeId())
        );
    }

    /**
     * 2) 인사팀 전체 징계 목록 조회
     *    - ROLE_HR 전용
     *    - GET /disciplinary
     */
    @PreAuthorize("hasRole('HR')")
    @GetMapping
    public ResponseEntity<List<MyDisciplinaryDTO>> getAllDisciplinary() {
        return ResponseEntity.ok(
                service.findAll()
        );
    }

    /**
     * 3) 징계 다운로드 URL 발급
     *    - ROLE_USER: 본인 것만, ROLE_HR: 모든 징계 다운로드 가능
     *    - GET /disciplinary/{id}/download
     */
    @PreAuthorize("hasAnyRole('USER','HR')")
    @GetMapping("/{id}/download")
    public ResponseEntity<Map<String,String>> download(
            @PathVariable("id") Integer disciplinaryId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        String url = service.generateDownloadUrl(disciplinaryId, user);
        return ResponseEntity.ok(Collections.singletonMap("url", url));
    }
}
