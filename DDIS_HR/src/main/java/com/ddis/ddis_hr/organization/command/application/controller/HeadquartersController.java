package com.ddis.ddis_hr.organization.command.application.controller;

import com.ddis.ddis_hr.organization.command.application.dto.HeadquartersDTO;
import com.ddis.ddis_hr.organization.command.application.dto.HeadquartersRequestDTO;
import com.ddis.ddis_hr.organization.command.application.service.HeadquartersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/org")
public class HeadquartersController {

    private final HeadquartersService headquartersService;

    public HeadquartersController(HeadquartersService headquartersService) {
        this.headquartersService = headquartersService;
    }

    /** 1) 본부 생성 (POST /api/headquarters) **/
    @PostMapping("/create/head")
    public ResponseEntity<HeadquartersDTO> createHeadquarters(
            @RequestBody HeadquartersRequestDTO requestDto) {
        HeadquartersDTO created = headquartersService.createHeadquarters(requestDto);
        return ResponseEntity.ok(created);
    }

    /** 4) 본부 수정 (PUT /api/headquarters/{id}) **/
    @PutMapping("/update/head/{id}")
    public ResponseEntity<HeadquartersDTO> updateHeadquarters(
            @PathVariable Long id,
            @RequestBody HeadquartersRequestDTO requestDto) {
        HeadquartersDTO updated = headquartersService.updateHeadquarters(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    /** 5) 본부 삭제 (DELETE /api/headquarters/{id}) **/
    @DeleteMapping("/delete/head/{id}")
    public ResponseEntity<Void> deleteHeadquarters(@PathVariable Long id) {
        headquartersService.deleteHeadquarters(id);
        return ResponseEntity.noContent().build();
    }
}
