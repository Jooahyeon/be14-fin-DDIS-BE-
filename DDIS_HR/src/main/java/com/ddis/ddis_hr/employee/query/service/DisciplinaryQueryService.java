package com.ddis.ddis_hr.employee.query.service;


import com.ddis.ddis_hr.employee.query.dto.MyDisciplinaryDTO;

import java.util.List;

public interface DisciplinaryQueryService {
    List<MyDisciplinaryDTO> findByEmployeeId(Long employeeId);
}
