package com.ddis.ddis_hr.employee.query.dao;

import com.ddis.ddis_hr.employee.query.dto.MyDisciplinaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DisciplinaryMapper {
    /** 본인 징계 목록 */
    List<MyDisciplinaryDTO> findByEmployeeId(@Param("employeeId") Long employeeId);

    /** 인사팀 전체 징계 목록 */
    List<MyDisciplinaryDTO> findAll();

    /** 단일 징계 조회 (메타·소유권 검사용) */
    MyDisciplinaryDTO findById(@Param("disciplinaryId") Integer disciplinaryId);
}
