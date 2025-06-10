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

    /** 전체 본부→부서→팀→팀원 계층 조회 */
    public List<HeadQueryDTO> getOrganizationHierarchy() {
        return structureMapper.selectAllHeadquarters();
    }
    public List<HeadQueryDTO> getAllHeads() {
        return structureMapper.selectAllHeads();
    }

    /** 특정 본부만 계층 조회 */
    public HeadQueryDTO getHeadHierarchy(Long headId) {
        return structureMapper.selectHeadById(headId);
    }

    /** 특정 부서만 계층 조회 */
    public DepartmentQueryDTO getDepartmentHierarchy(Long departmentId) {
        return structureMapper.selectDepartmentById(departmentId);
    }

    /** 특정 팀만 계층 조회 */
    public TeamQueryDTO getTeamHierarchy(Long teamId) {
        return structureMapper.selectTeamById(teamId);
    }

    /** 특정 팀의 팀원 목록만 조회 */
    public List<EmployeeQueryDTO> getMembersByTeam(Long teamId) {
        return structureMapper.selectMembersByTeamId(teamId);
    }

    /** 사원 ID로 사원 상세 조회 */
    public EmployeeQueryDTO getEmployeeById(Long employeeId) {
        return structureMapper.selectEmployeeById(employeeId);
    }


    public String getHeadNameById(Long headId) {
        return structureMapper.selectHeadNameById(headId);
    }
    public String getHeadCodeById(Long headId) {
        return structureMapper.selectHeadCodeById(headId);
    }


    /** 본부 ID를 받아, 해당 본부에 속한 모든 부서(DepartmentQueryDTO) 목록 조회
     *  - DepartmentQueryDTO 내부의 deptManager 필드는 XML 매퍼가 자동으로 채워 줌 */
    public List<DepartmentQueryDTO> getDepartmentsByHeadId(Long headId) {
        return structureMapper.selectDepartmentsByHeadId(headId);
    }

    /** 부서 ID를 받아, 해당 부서의 부장(Dept Manager) 한 명 조회 */
    public EmployeeQueryDTO getDeptManagerByDepartmentId(Long departmentId) {
        return structureMapper.selectDeptManagerByDepartmentId(departmentId);
    }
}
