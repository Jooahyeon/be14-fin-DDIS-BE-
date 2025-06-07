package com.ddis.ddis_hr.organization.command.application.controller;

import com.ddis.ddis_hr.organization.command.application.dto.HeadquartersDTO;
//import com.ddis.ddis_hr.organization.command.application.service.StructureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class StructureController {
//
//    private final StructureService structureService;
//
//    public StructureController(StructureService structureService) {
//        this.structureService = structureService;
//    }
//
//    /**
//     * 1) 조직도 전체 구조 조회
//     *    GET  /api/organization/tree
//     *    - 본부 → 부서 → 팀 계층을 모두 포함한 DTO 트리 구조 반환
//     */
//    @GetMapping("/tree")
//    public List<HeadquartersDTO> getHierarchy() {
//        return StructureService.getHierarchy();
//    }
}
