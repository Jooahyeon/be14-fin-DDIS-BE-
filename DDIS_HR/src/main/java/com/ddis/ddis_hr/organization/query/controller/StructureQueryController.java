package com.ddis.ddis_hr.organization.query.controller;

import com.ddis.ddis_hr.organization.query.dto.DepartmentQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.EmployeeQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.HeadQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.TeamQueryDTO;
import com.ddis.ddis_hr.organization.query.service.StructureQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/structure")
public class StructureQueryController {

    private final StructureQueryService structureQueryService;

    @Autowired
    public StructureQueryController(StructureQueryService structureQueryService) {
        this.structureQueryService = structureQueryService;
    }


    // 본부 전체 계층(부서→팀→팀원) 조회
    @GetMapping("/hierarchy")
    public List<HeadQueryDTO> getFullHierarchy() {
        return structureQueryService.getOrganizationHierarchy();
    }


     // 특정 본부 계층(부서→팀→팀원)만 조회
    @GetMapping("/heads/{headId}")
    public HeadQueryDTO getHeadHierarchy(@PathVariable("headId") Long headId) {
        return structureQueryService.getHeadHierarchy(headId);
    }

    // 특정 부서 계층(팀→팀원)만 조회
    @GetMapping("/departments/{deptId}")
    public DepartmentQueryDTO getDepartmentHierarchy(@PathVariable("deptId") Long deptId) {
        return structureQueryService.getDepartmentHierarchy(deptId);
    }

    // 특정 팀 계층(팀원)만 조회
    @GetMapping("/teams/{teamId}")
    public TeamQueryDTO getTeamHierarchy(@PathVariable("teamId") Long teamId) {
        return structureQueryService.getTeamHierarchy(teamId);
    }

    // 특정 팀의 팀원 목록만 조회
    @GetMapping("/teams/{teamId}/members")
    public List<EmployeeQueryDTO> getMembers(@PathVariable("teamId") Long teamId) {
        return structureQueryService.getMembersByTeam(teamId);
    }
}
