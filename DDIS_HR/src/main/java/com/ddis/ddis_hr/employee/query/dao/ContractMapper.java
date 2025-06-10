package com.ddis.ddis_hr.employee.query.dao;

import com.ddis.ddis_hr.employee.query.dto.MyContractDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractMapper {
    /** 본인 계약서 목록 */
    List<MyContractDTO> findByEmployeeId(@Param("employeeId") Long employeeId);

    /** 인사팀 전체 계약서 목록 */
    List<MyContractDTO> findAllContracts();

    /** 단일 계약서 조회 (메타·소유권 검사용) */
    MyContractDTO findById(@Param("contractId") Long contractId);
}
