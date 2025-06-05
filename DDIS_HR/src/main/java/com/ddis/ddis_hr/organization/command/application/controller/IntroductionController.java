package com.ddis.ddis_hr.organization.command.application.controller;

import com.ddis.ddis_hr.organization.command.application.dto.CreateIntroductionDTO;
import com.ddis.ddis_hr.organization.command.application.dto.IntroductionResponseDTO;
import com.ddis.ddis_hr.organization.command.application.dto.UpdateIntroductionDTO;
import com.ddis.ddis_hr.organization.command.application.service.IntroductionService;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.IntroductionEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/introduction")
public class IntroductionController {
    private final IntroductionService introductionService;

    public IntroductionController(IntroductionService introductionService) {
        this.introductionService = introductionService;
    }

    // 부서 소개 등록
    @PostMapping("/create/department/{departmentId}")
    public ResponseEntity<IntroductionResponseDTO> createDepartmentIntroduction(
            @PathVariable("departmentId") Long deptId,
            @RequestBody CreateIntroductionDTO request) {

        IntroductionEntity saved = introductionService.createDepartmentIntroduction(
                deptId, request.getIntroductionContext());

        IntroductionResponseDTO response = new IntroductionResponseDTO(
                saved.getIntroductionId(),
                saved.getDepartmentId(),
                saved.getTeamId(),          // 부서 소개이므로 null
                saved.getIntroductionContext(),
                saved.getCreatedAt()
        );

        URI location = URI.create(String.format("/department/%d", deptId));
        return ResponseEntity.created(location).body(response);
    }


    // 부서 소개 수정
    @PutMapping("/update/department/{departmentId}")
    public ResponseEntity<IntroductionResponseDTO> updateDepartmentIntroduction(
            @PathVariable("departmentId") Long deptId,
            @RequestBody UpdateIntroductionDTO request) {

        IntroductionEntity updated = introductionService.updateDepartmentIntroduction(
                deptId, request.getIntroductionContext());

        IntroductionResponseDTO response = new IntroductionResponseDTO(
                updated.getIntroductionId(),
                updated.getDepartmentId(),
                updated.getTeamId(),
                updated.getIntroductionContext(),
                updated.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    // 부서 소개 삭제
    @DeleteMapping("/delete/department/{departmentId}")
    public ResponseEntity<Void> deleteDepartmentIntroduction(
            @PathVariable("departmentId") Long deptId) {

        introductionService.deleteDepartmentIntroduction(deptId);
        return ResponseEntity.noContent().build();
    }


    // 팀 소개 등록
    @PostMapping("/team/{teamId}")
    public ResponseEntity<IntroductionResponseDTO> createTeamIntroduction(
            @PathVariable("teamId") Long teamId,
            @RequestBody CreateIntroductionDTO request) {

        IntroductionEntity saved = introductionService.createTeamIntroduction(
                teamId, request.getIntroductionContext());

        IntroductionResponseDTO response = new IntroductionResponseDTO(
                saved.getIntroductionId(),
                saved.getDepartmentId(),
                saved.getTeamId(),
                saved.getIntroductionContext(),
                saved.getCreatedAt()
        );

        URI location = URI.create(String.format("/team/%d", teamId));
        return ResponseEntity.created(location).body(response);
    }

    // 팀 소개 수정
    @PutMapping("/team/{teamId}")
    public ResponseEntity<IntroductionResponseDTO> updateTeamIntroduction(
            @PathVariable("teamId") Long teamId,
            @RequestBody UpdateIntroductionDTO request) {

        IntroductionEntity updated = introductionService.updateTeamIntroduction(
                teamId, request.getIntroductionContext());

        IntroductionResponseDTO response = new IntroductionResponseDTO(
                updated.getIntroductionId(),
                updated.getDepartmentId(),
                updated.getTeamId(),
                updated.getIntroductionContext(),
                updated.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    // 팀 소개 삭제
    @DeleteMapping("/team/{teamId}")
    public ResponseEntity<Void> deleteTeamIntroduction(
            @PathVariable("teamId") Long teamId) {

        introductionService.deleteTeamIntroduction(teamId);
        return ResponseEntity.noContent().build();
    }
}
