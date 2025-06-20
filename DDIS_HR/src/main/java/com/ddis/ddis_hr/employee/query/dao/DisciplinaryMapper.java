package com.ddis.ddis_hr.employee.query.dao;

import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DisciplinaryMapper {
    List<DisciplinaryListDTO> selectAll();

    List<DisciplinaryListDTO> selectByEmployeeId(@Param("employeeId") Long employeeId);
}
