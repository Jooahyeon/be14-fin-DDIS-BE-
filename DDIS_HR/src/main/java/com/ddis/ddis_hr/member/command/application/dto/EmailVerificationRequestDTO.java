package com.ddis.ddis_hr.member.command.application.dto;


import lombok.Data;

@Data
public class EmailVerificationRequestDTO {
    private String email;
    private String verificationCode;
}
