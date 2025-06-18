package com.ddis.ddis_hr.organization.command.application.controller;

import com.ddis.ddis_hr.organization.command.application.dto.*;
//import com.ddis.ddis_hr.organization.command.application.service.StructureService;
import com.ddis.ddis_hr.organization.command.application.service.DepartmentService;
import com.ddis.ddis_hr.organization.command.application.service.HeadquartersService;
import com.ddis.ddis_hr.organization.command.application.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/org")
public class StructureController {

    private final HeadquartersService headquartersService;
    private final DepartmentService departmentService;
    private final TeamService teamService;

    public StructureController(
            HeadquartersService headquartersService,
            DepartmentService departmentService,
            TeamService teamService
    ) {
        this.headquartersService = headquartersService;
        this.departmentService  = departmentService;
        this.teamService        = teamService;
    }

    // 본부
    /** 본부 생성 **/
    @PreAuthorize("hasAnyRole('HR')")
    @PostMapping("/create/head")
    public ResponseEntity<HeadquartersDTO> createHeadquarters(
            @RequestBody HeadquartersRequestDTO requestDto) {
        HeadquartersDTO created = headquartersService.createHeadquarters(requestDto);
        return ResponseEntity.ok(created);
    }

    /** 본부 수정 **/
    @PreAuthorize("hasAnyRole('HR')")
    @PutMapping("/update/head/{id}")
    public ResponseEntity<HeadquartersDTO> updateHeadquarters(
            @PathVariable Long id,
            @RequestBody HeadquartersRequestDTO requestDto) {
        HeadquartersDTO updated = headquartersService.updateHeadquarters(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    /** 본부 삭제 **/
    @PreAuthorize("hasAnyRole('HR')")
    @DeleteMapping("/delete/head/{id}")
    public ResponseEntity<Void> deleteHeadquarters(@PathVariable Long id) {
        headquartersService.deleteHeadquarters(id);
        return ResponseEntity.noContent().build();
    }

    // 부서
    /** 부서 생성 **/
    @PreAuthorize("hasAnyRole('HR')")
    @PostMapping("/create/department")
    public ResponseEntity<DepartmentDTO> createDepartment(
            @RequestBody DepartmentRequestDTO requestDto) {
        DepartmentDTO created = departmentService.createDepartment(requestDto);
        return ResponseEntity.ok(created);
    }

    /** 부서 수정 **/
    @PreAuthorize("hasAnyRole('HR')")
    @PutMapping("/update/department/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentRequestDTO requestDto) {
        DepartmentDTO updated = departmentService.updateDepartment(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    /** 부서 삭제 **/
    @PreAuthorize("hasAnyRole('HR')")
    @DeleteMapping("/delete/department/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    // 팀
    /** 팀 생성 **/
    @PreAuthorize("hasAnyRole('HR')")
    @PostMapping("/create/team")
    public ResponseEntity<TeamDTO> createTeam(
            @RequestBody TeamRequestDTO requestDto) {
        TeamDTO created = teamService.createTeam(requestDto);
        return ResponseEntity.ok(created);
    }

    /** 팀 수정 **/
    @PreAuthorize("hasAnyRole('HR')")
    @PutMapping("/update/team/{id}")
    public ResponseEntity<TeamDTO> updateTeam(
            @PathVariable Long id,
            @RequestBody TeamRequestDTO requestDto) {
        TeamDTO updated = teamService.updateTeam(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    /** 팀 삭제 **/
    @PreAuthorize("hasAnyRole('HR')")
    @DeleteMapping("/delete/team/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
