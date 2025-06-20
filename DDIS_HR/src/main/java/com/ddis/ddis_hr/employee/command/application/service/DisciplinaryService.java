package com.ddis.ddis_hr.employee.command.application.service;

import com.ddis.ddis_hr.employee.command.application.dto.DisciplinaryEnrollDTO;

public interface DisciplinaryService {

    Long createDisciplinary(DisciplinaryEnrollDTO dto);

    void deleteDisciplinary(Long disciplinaryId);
}

