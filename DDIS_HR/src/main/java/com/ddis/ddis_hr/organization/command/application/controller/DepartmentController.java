package com.ddis.ddis_hr.organization.command.application.controller;

import com.ddis.ddis_hr.organization.command.application.dto.DepartmentDTO;
import com.ddis.ddis_hr.organization.command.application.dto.DepartmentRequestDTO;
import com.ddis.ddis_hr.organization.command.application.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/org")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /** 1) 부서 생성 (POST /api/departments) **/
    @PostMapping("/create/department")
    public ResponseEntity<DepartmentDTO> createDepartment(
            @RequestBody DepartmentRequestDTO requestDto) {
        DepartmentDTO created = departmentService.createDepartment(requestDto);
        return ResponseEntity.ok(created);
    }

    /** 4) 부서 수정 (PUT /api/departments/{id}) **/
    @PutMapping("/update/department/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentRequestDTO requestDto) {
        DepartmentDTO updated = departmentService.updateDepartment(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    /** 5) 부서 삭제 (DELETE /api/departments/{id}) **/
    @DeleteMapping("/delete/department/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
