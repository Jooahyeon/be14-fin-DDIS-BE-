package com.ddis.ddis_hr.payroll.command.application.service;

import com.ddis.ddis_hr.payroll.command.application.dto.RetirementMailRequestDTO;

public interface RetirementMailService {
    void sendRetirementByEmail(RetirementMailRequestDTO dto);
}
