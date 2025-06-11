package com.ddis.ddis_hr.organization.query.dao;

import com.ddis.ddis_hr.organization.query.dto.DepartmentQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.EmployeeQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.HeadQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.TeamQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface StructureMapper {

    // 1) 전체 본부 목록 조회 → 각 본부별로 소속 부서, 소속 팀, 팀원까지 계층적으로 조회
    List<HeadQueryDTO> selectAllHeadquarters();
    List<HeadQueryDTO> selectAllHeads();

    // 2) 특정 본부(Head) 하나 상세 조회 (부서, 팀, 팀원까지 계층 포함)
    HeadQueryDTO selectHeadById(@Param("headId") Long headId);

    // 3) 특정 부서(Department) 하나 상세 조회 (소속 팀, 팀원까지 계층 포함)
    DepartmentQueryDTO selectDepartmentById(@Param("departmentId") Long departmentId);


    // 4) 특정 팀(Team) 하나 상세 조회 (팀원 목록 포함)
    TeamQueryDTO selectTeamById(@Param("teamId") Long teamId);


    // 5) 특정 팀ID에 소속된 팀원(Employee) 전체 조회
    List<EmployeeQueryDTO> selectMembersByTeamId(@Param("teamId") Long teamId);


    // 사원 ID로 사원 상세 조회
    EmployeeQueryDTO selectEmployeeById(@Param("employeeId") Long employeeId);

    // 부서 ID를 받아, 해당 부서의 모든 팀(Team) 목록 조회
    List<TeamQueryDTO> selectTeamsByDepartmentId(@Param("departmentId") Long departmentId);

    // 본부 ID를 받아, 해당 본부의 모든 부서(Department) 목록 조회
    List<DepartmentQueryDTO> selectDepartmentsByHeadId(@Param("headId") Long headId);

    // 부서 바로 아래 부장(Dept Manager) 한 명만 조회하는 쿼리
    EmployeeQueryDTO selectDeptManagerByDepartmentId(@Param("departmentId") Long departmentId);

    // headId로 headName만 조회
    String selectHeadNameById(@Param("headId") Long headId);

    //headId로 headCode만 조회
    String selectHeadCodeById(@Param("headId") Long headId);

    List<EmployeeQueryDTO> selectDeptMembers(Long departmentId);

}

