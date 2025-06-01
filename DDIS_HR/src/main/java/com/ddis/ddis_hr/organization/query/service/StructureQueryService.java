package com.ddis.ddis_hr.organization.query.service;

import com.ddis.ddis_hr.organization.query.dao.StructureMapper;
import com.ddis.ddis_hr.organization.query.dto.DepartmentQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.EmployeeQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.HeadQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.TeamQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StructureQueryService {

    private final StructureMapper structureMapper;

    @Autowired
    public StructureQueryService(StructureMapper structureMapper) {
        this.structureMapper = structureMapper;
    }

    /**
     * 전체 본부→부서→팀→팀원 계층 조회
     */
    public List<HeadQueryDTO> getOrganizationHierarchy() {
        return structureMapper.selectAllHeadquarters();
    }

    /**
     * 특정 본부만 계층 조회
     */
    public HeadQueryDTO getHeadHierarchy(Long headId) {
        return structureMapper.selectHeadById(headId);
    }

    /**
     * 특정 부서만 계층 조회
     */
    public DepartmentQueryDTO getDepartmentHierarchy(Long departmentId) {
        return structureMapper.selectDepartmentById(departmentId);
    }

    /**
     * 특정 팀만 계층 조회
     */
    public TeamQueryDTO getTeamHierarchy(Long teamId) {
        return structureMapper.selectTeamById(teamId);
    }

    /**
     * 특정 팀의 팀원 목록만 조회
     */
    public List<EmployeeQueryDTO> getMembersByTeam(Long teamId) {
        return structureMapper.selectMembersByTeamId(teamId);
    }
}
