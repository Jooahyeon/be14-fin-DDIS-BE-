package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.TeamDTO;
import com.ddis.ddis_hr.organization.command.application.dto.TeamRequestDTO;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.DepartmentEntity;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.TeamEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.DepartmentRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;

    public TeamServiceImpl(TeamRepository teamRepository, DepartmentRepository departmentRepository) {
        this.teamRepository = teamRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public TeamDTO createTeam(TeamRequestDTO requestDto) {
        // 1) departmentName으로 부서 조회
        DepartmentEntity dept = departmentRepository
                .findByDepartmentId(requestDto.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("해당 이름의 부서를 찾을 수 없습니다: " + requestDto.getDepartmentId()));

        // 2) teamCode 자동 생성
        TeamEntity last = teamRepository.findTopByOrderByTeamCodeDesc();
        String newCode;
        if (last == null) {
            newCode = "T001";
        } else {
            String lastCode = last.getTeamCode(); // 예: "T007"
            int num = Integer.parseInt(lastCode.substring(1)); // 7
            newCode = String.format("T%03d", num + 1);         // "T008"
        }

        // 3) 엔티티에 데이터 설정
        TeamEntity entity = new TeamEntity();
        entity.setTeamName(requestDto.getTeamName());
        entity.setTeamCode(newCode);
        entity.setDepartment(dept);

        // 4) 저장 후 DTO 반환
        TeamEntity saved = teamRepository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public TeamDTO updateTeam(Long id, TeamRequestDTO requestDto) {
        TeamEntity existing = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("팀을 찾을 수 없습니다: " + id));

        // 1) departmentName으로 부서 조회
        DepartmentEntity dept = departmentRepository
                .findByDepartmentId(requestDto.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("해당 이름의 부서를 찾을 수 없습니다: " + requestDto.getDepartmentId()));

        // 2) 부서 연결 변경
        existing.setDepartment(dept);

        // 3) 팀명 수정 (팀 코드는 서비스가 자동 생성된 값을 유지)
        existing.setTeamName(requestDto.getTeamName());

        TeamEntity saved = teamRepository.save(existing);
        return mapToDto(saved);
    }

    @Override
    public void deleteTeam(Long id) {
        TeamEntity existing = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found: " + id));
        teamRepository.delete(existing);
    }

    private TeamDTO mapToDto(TeamEntity entity) {
        TeamDTO dto = new TeamDTO();
        dto.setTeamId(entity.getTeamId());
        dto.setTeamName(entity.getTeamName());
        dto.setTeamCode(entity.getTeamCode());
        return dto;
    }
}
