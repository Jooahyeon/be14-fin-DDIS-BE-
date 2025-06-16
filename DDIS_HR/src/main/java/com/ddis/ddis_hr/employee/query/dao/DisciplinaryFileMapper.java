package com.ddis.ddis_hr.employee.query.dao;

import com.ddis.ddis_hr.employee.query.dto.DisciplinaryFileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DisciplinaryFileMapper {
    List<DisciplinaryFileDTO> findByDisciplinaryId(@Param("disciplinaryId") Long disciplinaryId);
}
