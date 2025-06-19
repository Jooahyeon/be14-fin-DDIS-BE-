package com.ddis.ddis_hr.employee.command.application.controller;

import com.ddis.ddis_hr.employee.command.application.dto.DisciplinaryEnrollDTO;
import com.ddis.ddis_hr.employee.command.application.service.DisciplinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplinary")
public class DisciplinaryController {

    private final DisciplinaryService disciplinaryService;

    @Autowired
    public DisciplinaryController(DisciplinaryService disciplinaryService) {
        this.disciplinaryService = disciplinaryService;
    }

    // 징계 등록
    @PostMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Long> create(
            @RequestBody DisciplinaryEnrollDTO dto) {
        Long id = disciplinaryService.createDisciplinary(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    // 징계 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id
    ) {
        disciplinaryService.deleteDisciplinary(id);
        return ResponseEntity.noContent().build();
    }
}

