package com.ddis.ddis_hr.member.command.application.service;


import com.samsung.dieat.member.command.application.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void registUser(UserDTO userDTO);

    UserDTO getUserById(String memNo);
}
