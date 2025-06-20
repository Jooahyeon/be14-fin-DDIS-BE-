package com.ddis.ddis_hr.employee.query.controller;

import com.ddis.ddis_hr.employee.query.dto.EmployeeHrDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeeListDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeePublicDTO;
import com.ddis.ddis_hr.employee.query.service.EmployeeQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.employee.query.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeQueryController {

    private final EmployeeQueryService employeeQueryService;

    @Autowired
    public EmployeeQueryController(EmployeeQueryService employeeQueryService) {
        this.employeeQueryService = employeeQueryService;
    }

     // 본인 정보 상세 조회
     @GetMapping("/myinfo")
     public ResponseEntity<EmployeeDTO> getMyProfile(@AuthenticationPrincipal CustomUserDetails user) {
         EmployeeDTO dto = employeeQueryService.findByMyId(user.getEmployeeId());
         return ResponseEntity.ok(dto);
     }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        boolean isHr = user.getAuthorities().stream()
                .anyMatch(a -> "ROLE_HR".equals(a.getAuthority()));

        if (isHr) {
            EmployeeHrDTO hrDto = employeeQueryService.getHrById(id);
            return ResponseEntity.ok(hrDto);
        } else {
            EmployeePublicDTO pubDto = employeeQueryService.getPublicById(id);
            return ResponseEntity.ok(pubDto);
        }
    }

    // 사원 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<EmployeeListDTO>> listAll(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        // 1) 인증 정보가 없으면 401 Unauthorized
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        // 2) 서비스 호출하여 사원 목록 조회
        List<EmployeeListDTO> list = employeeQueryService.getAll();
        // 3) 200 OK 로 응답
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> searchByName(
            @RequestParam("name") String name
    ) {
        List<EmployeeDTO> results = employeeQueryService.searchByName(name);
        return ResponseEntity.ok(results);
    }
}

