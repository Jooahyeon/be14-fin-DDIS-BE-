package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import java.util.List;

public interface DisciplinaryQueryService {

    List<DisciplinaryListDTO> findAll();

    List<DisciplinaryListDTO> findByEmployeeId(Long employeeId);
}
