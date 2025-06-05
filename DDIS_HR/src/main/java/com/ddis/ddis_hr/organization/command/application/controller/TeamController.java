package com.ddis.ddis_hr.organization.command.application.controller;

import com.ddis.ddis_hr.organization.command.application.dto.TeamDTO;
import com.ddis.ddis_hr.organization.command.application.dto.TeamRequestDTO;
import com.ddis.ddis_hr.organization.command.application.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/org")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /** 1) 팀 생성 (POST /api/teams) **/
    @PostMapping("/create/team")
    public ResponseEntity<TeamDTO> createTeam(
            @RequestBody TeamRequestDTO requestDto) {
        TeamDTO created = teamService.createTeam(requestDto);
        return ResponseEntity.ok(created);
    }

    /** 4) 팀 수정 (PUT /api/teams/{id}) **/
    @PutMapping("/update/team/{id}")
    public ResponseEntity<TeamDTO> updateTeam(
            @PathVariable Long id,
            @RequestBody TeamRequestDTO requestDto) {
        TeamDTO updated = teamService.updateTeam(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    /** 5) 팀 삭제 (DELETE /api/teams/{id}) **/
    @DeleteMapping("/delete/team/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
