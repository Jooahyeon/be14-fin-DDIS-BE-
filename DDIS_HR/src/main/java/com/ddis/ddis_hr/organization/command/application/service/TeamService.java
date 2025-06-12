package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.TeamDTO;
import com.ddis.ddis_hr.organization.command.application.dto.TeamRequestDTO;

public interface TeamService {

    TeamDTO createTeam(TeamRequestDTO requestDto);

    TeamDTO updateTeam(Long id, TeamRequestDTO requestDto);

    void deleteTeam(Long id);
}
