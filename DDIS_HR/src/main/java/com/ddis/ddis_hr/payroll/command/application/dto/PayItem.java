package com.ddis.ddis_hr.payroll.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayItem {
    private String label;
    private int amount;
}