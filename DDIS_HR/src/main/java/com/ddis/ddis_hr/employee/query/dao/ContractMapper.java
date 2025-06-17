package com.ddis.ddis_hr.employee.query.dao;

import com.ddis.ddis_hr.employee.query.dto.ContractListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractMapper {
    /** 전체 계약서 목록 조회 */
    List<ContractListDTO> selectAll();

    /** 사원별 계약서 목록 조회 */
    List<ContractListDTO> selectByEmployeeId(@Param("employeeId") Long employeeId);
}
