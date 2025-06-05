package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.DepartmentDTO;
import com.ddis.ddis_hr.organization.command.application.dto.DepartmentRequestDTO;

public interface DepartmentService {

    DepartmentDTO createDepartment(DepartmentRequestDTO requestDto);

    DepartmentDTO updateDepartment(Long id, DepartmentRequestDTO requestDto);

    void deleteDepartment(Long id);
}
