package com.ddis.ddis_hr.employee.query.controller;

import com.ddis.ddis_hr.employee.query.dto.EmployeeListDTO;
import com.ddis.ddis_hr.employee.query.service.EmployeeQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.employee.query.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeQueryController {

    private final EmployeeQueryService employeeQueryService;

    @Autowired
    public EmployeeQueryController(EmployeeQueryService employeeQueryService) { //@Autowired를 통해 생성자 주입 방식으로
        this.employeeQueryService = employeeQueryService;                       // employeeService의 인스턴스를 주입 받는다.
    }

     // 본인 정보 상세 조회
     @GetMapping("/myinfo")
     public ResponseEntity<EmployeeDTO> getMyProfile(@AuthenticationPrincipal CustomUserDetails user) {
         EmployeeDTO dto = employeeQueryService.findByMyId(user.getEmployeeId());
         return ResponseEntity.ok(dto);
     }

// 타 사원 정보 상세 조회
//     @GetMapping("/{id}")
//     public ResponseEntity<?> getEmployeeById(
//             @PathVariable Long id,
//             @AuthenticationPrincipal CustomUserDetails user
//     ) {
//         Object result = employeeQueryService.findByIdWithRole(id, user.getAuthorities());
//         return ResponseEntity.ok(result);
//     }

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
}

