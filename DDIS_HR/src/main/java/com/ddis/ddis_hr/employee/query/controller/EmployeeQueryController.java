package com.ddis.ddis_hr.employee.query.controller;

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

    /**
     * 사원 상세 조회
     * - path로 사번(employeeId)을 받아서 DTO 리턴
     */
//    @GetMapping("/{employeeId}")
//    public ResponseEntity<EmployeeDTO> getEmployeeDetail(
//             @PathVariable Long employeeId
//            ,@AuthenticationPrincipal CustomUserDetails user ) {
//        EmployeeDTO dto = employeeQueryService.findById(employeeId);
//        return ResponseEntity.ok(dto);
//    }

     // 본인 프로필 조회하게 하려면 아래처럼도 가능
     @GetMapping("/me")
     public ResponseEntity<EmployeeDTO> getMyProfile(@AuthenticationPrincipal CustomUserDetails user) {
         EmployeeDTO dto = employeeQueryService.findByMyId(user.getEmployeeId());
         return ResponseEntity.ok(dto);
     }
}

