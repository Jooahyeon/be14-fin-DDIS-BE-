package com.ddis.ddis_hr.employee.query.dao;

import com.ddis.ddis_hr.employee.query.dto.MyContractDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractMapper {

     // 로그인한 사원의 계약서 목록 조회
    List<MyContractDTO> findByEmployeeId(@Param("employeeId") Long employeeId);
}

