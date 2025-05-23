package com.ddis.ddis_hr.member.command.application.service;


import com.ddis.ddis_hr.member.command.application.dto.UserDTO;
import com.ddis.ddis_hr.member.command.domain.repository.UserRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    ModelMapper modelMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void registUser(UserDTO userDTO) {
        userDTO.setUserEnrollDt(new java.util.Date());

        Employee registUser = modelMapper.map(userDTO, Employee.class);
        registUser.setEncryptedPwd(bCryptPasswordEncoder.encode(userDTO.getUserPwd()));

        userRepository.save(registUser);
    }

    @Override
    public UserDTO getUserById(String memNo) {
        Employee foundUser = userRepository.findById(Long.parseLong(memNo)).get();

        UserDTO userDTO = modelMapper.map(foundUser, UserDTO.class);

        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Employee loginUser = userRepository.findByUserId(userId);

        if(loginUser == null) {
            throw new UsernameNotFoundException(userId + "아이디가 존재하지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if("ADMIN".equals(loginUser.getRole())) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new CustomUserDetails(
                loginUser.getUserId(),
                loginUser.getEncryptedPwd(),
                loginUser.getUserCode(),
                grantedAuthorities
        );
    }
}
