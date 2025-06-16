package com.ddis.ddis_hr.employee.query.controller;

import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import com.ddis.ddis_hr.employee.query.service.DisciplinaryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinary")
@RequiredArgsConstructor
public class DisciplinaryQueryController {

    private final DisciplinaryQueryService service;

//    @PreAuthorize("hasRole('HR')")
    @GetMapping
    public ResponseEntity<List<DisciplinaryListDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }
}
