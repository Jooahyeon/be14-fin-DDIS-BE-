package com.ddis.ddis_hr.member.command.application.service;


import com.ddis.ddis_hr.member.command.application.dto.EmployeeDTO;
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
@Transactional
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
    public void registUser(EmployeeDTO employeeDTO) {

        Employee registUser = modelMapper.map(employeeDTO, Employee.class);
        registUser.setEmployeePwd(bCryptPasswordEncoder.encode(employeeDTO.getEmployeePwd()));

        userRepository.save(registUser);
    }

    @Override
    public EmployeeDTO getUserById(String employeeId) {
        Employee foundUser = userRepository.findById(Long.parseLong(employeeId)).get();

        EmployeeDTO employeeDTO = modelMapper.map(foundUser, EmployeeDTO.class);

        return employeeDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {

        Employee loginUser = userRepository.findById(Long.parseLong(employeeId)).get();

        if(loginUser == null) {
            throw new UsernameNotFoundException(employeeId + "아이디가 존재하지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if("팀장".equals(loginUser.getPosition().getPositionName())) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_TEAMLEADER"));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        if("인사평가팀".equals(loginUser.getTeam().getTeamName())) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_HR"));
        }
        return new CustomUserDetails(
                loginUser.getEmployeeId(),
                loginUser.getEmployeePwd(),
                loginUser.getPosition().getPositionId(),
                loginUser.getRank().getRankId(),
                loginUser.getJob().getJobId(),
                loginUser.getHeadquarters().getHeadId(),
                loginUser.getDepartment().getDepartmentId(),
                loginUser.getTeam().getTeamId(),
                grantedAuthorities
        );
    }
}
