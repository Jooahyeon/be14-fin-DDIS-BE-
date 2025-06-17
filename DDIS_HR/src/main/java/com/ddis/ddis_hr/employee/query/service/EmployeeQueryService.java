package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dto.EmployeeDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeeHrDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeeListDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeePublicDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface EmployeeQueryService {
    EmployeeDTO findByMyId(Long employeeId);        //본인 정보 조회

    EmployeePublicDTO getPublicById(Long id);

    EmployeeHrDTO getHrById(Long id);

    Object findByIdWithRole(Long id, Collection<? extends GrantedAuthority> authorities);

    List<EmployeeListDTO> getAll();

    List<EmployeeDTO> searchByName(String name);
}
