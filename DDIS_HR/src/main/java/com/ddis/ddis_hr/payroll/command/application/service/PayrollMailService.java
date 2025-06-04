package com.ddis.ddis_hr.payroll.command.application.service;

import com.ddis.ddis_hr.payroll.command.application.dto.PayrollMailRequestDTO;

public interface PayrollMailService {
    void sendPaystubByEmail(PayrollMailRequestDTO dto);
}
