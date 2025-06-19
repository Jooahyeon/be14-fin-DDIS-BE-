package com.ddis.ddis_hr.employee.command.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateDTO {
    private String employeePhotoName;
    private String employeePhotoUrl;
    private LocalDate employeeBirth;
    private String employeeContact;
    private String employeeEmail;
    private String employeeAddress;
    private String bankName;
    private String bankDepositor;
    private String bankAccount;
}
