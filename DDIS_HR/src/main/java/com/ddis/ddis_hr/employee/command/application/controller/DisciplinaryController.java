package com.ddis.ddis_hr.employee.command.application.controller;

import com.ddis.ddis_hr.employee.command.application.dto.DisciplinaryEnrollDTO;
import com.ddis.ddis_hr.employee.command.application.service.DisciplinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplinary")
@RequiredArgsConstructor
public class DisciplinaryController {

    private final DisciplinaryService service;

    /** 징계 등록 */
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DisciplinaryEnrollDTO dto) {
        Long id = service.createDisciplinary(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    /** 징계 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.deleteDisciplinary(id);
        return ResponseEntity.noContent().build();
    }
}

