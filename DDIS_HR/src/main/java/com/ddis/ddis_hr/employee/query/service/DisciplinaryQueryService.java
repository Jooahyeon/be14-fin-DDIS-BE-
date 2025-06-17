package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import java.util.List;

public interface DisciplinaryQueryService {
    /** 전체 징계 목록 조회 */
    List<DisciplinaryListDTO> findAll();

    /** 사원별 징계 목록 조회 */
    List<DisciplinaryListDTO> findByEmployeeId(Long employeeId);
}
