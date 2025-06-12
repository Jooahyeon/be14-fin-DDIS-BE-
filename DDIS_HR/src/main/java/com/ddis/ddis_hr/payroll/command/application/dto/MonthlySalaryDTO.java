package com.ddis.ddis_hr.payroll.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySalaryDTO {
    private String label;
    private Integer amount;
}