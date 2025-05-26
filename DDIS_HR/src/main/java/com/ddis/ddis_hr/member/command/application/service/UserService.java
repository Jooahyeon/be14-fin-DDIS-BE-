package com.ddis.ddis_hr.member.command.application.service;


import com.ddis.ddis_hr.member.command.application.dto.EmployeeDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void registUser(EmployeeDTO employeeDTO);

    EmployeeDTO getUserById(String memNo);
}
