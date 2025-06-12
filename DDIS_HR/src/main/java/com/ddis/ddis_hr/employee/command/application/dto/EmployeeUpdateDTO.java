package com.ddis.ddis_hr.employee.command.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * 일반 사원이 수정할 수 있는 필드만 담는 DTO
 */
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
