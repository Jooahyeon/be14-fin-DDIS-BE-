package com.ddis.ddis_hr.employee.query.dao;

import com.ddis.ddis_hr.employee.query.dto.MyContractDTO;
import com.ddis.ddis_hr.employee.query.dto.MyDisciplinaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DisciplinaryMapper {
    List<MyDisciplinaryDTO> findByEmployeeId(@Param("employeeId") Long employeeId);
}
