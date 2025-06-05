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
    /**
     * 1) 전체 본부 목록 조회
     *    → 각 본부별로 소속 부서, 소속 팀, 팀원까지 계층적으로 조회
     */
    List<HeadQueryDTO> selectAllHeadquarters();


    /**
     * 2) 특정 본부(Head) 하나 상세 조회
     *    (부서, 팀, 팀원까지 계층 포함)
     */
    HeadQueryDTO selectHeadById(@Param("headId") Long headId);


    /**
     * 3) 특정 부서(Department) 하나 상세 조회
     *    (소속 팀, 팀원까지 계층 포함)
     */
    DepartmentQueryDTO selectDepartmentById(@Param("departmentId") Long departmentId);


    /**
     * 4) 특정 팀(Team) 하나 상세 조회
     *    (팀원 목록 포함)
     */
    TeamQueryDTO selectTeamById(@Param("teamId") Long teamId);


    /**
     * 5) 특정 팀ID에 소속된 팀원(Employee) 전체 조회
     */
    List<EmployeeQueryDTO> selectMembersByTeamId(@Param("teamId") Long teamId);

    /**
     * 사원 ID로 사원 상세 조회
     * @param employeeId 조회할 사원 고유번호
     * @return EmployeeQueryDTO (사원 정보가 없으면 null 리턴)
     */
    EmployeeQueryDTO selectEmployeeById(@Param("employeeId") Long employeeId);

    List<HeadQueryDTO> selectAllHeads();

    String selectHeadNameById(@Param("headId") Long headId);
    String selectHeadCodeById(@Param("headId") Long headId);
}

