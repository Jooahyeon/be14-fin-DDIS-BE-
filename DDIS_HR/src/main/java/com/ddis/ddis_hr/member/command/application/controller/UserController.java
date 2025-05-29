package com.ddis.ddis_hr.member.command.application.controller;


import com.ddis.ddis_hr.member.command.application.dto.EmployeeDTO;
import com.ddis.ddis_hr.member.command.application.service.UserService;
import com.ddis.ddis_hr.member.command.domain.aggregate.vo.ResponseFindUserVO;
import com.ddis.ddis_hr.member.command.domain.aggregate.vo.ResponseRegistUserVO;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    private Environment env;
    private UserService userService;
    private ModelMapper modelMapper;


    @Autowired
    public UserController(Environment env, UserService userService, ModelMapper modelMapper) {
        this.env = env;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/health")
    public String status(){
        return "응애!!!!" + env.getProperty("local.server.port");
    }

    @PostMapping("users")
    public ResponseEntity<ResponseRegistUserVO> registUser(@RequestBody EmployeeDTO newUser) {

        EmployeeDTO employeeDTO = modelMapper.map(newUser, EmployeeDTO.class);


        userService.registUser(employeeDTO);
        ResponseRegistUserVO successRegistUser = modelMapper.map(employeeDTO, ResponseRegistUserVO.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(successRegistUser);



    }

    @GetMapping("/users/{employeeId}")
    public ResponseEntity<ResponseFindUserVO> getUsers(@PathVariable String employeeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserId = auth.getName();

        EmployeeDTO employeeDTO = userService.getUserById(employeeId);

        if (!loggedInUserId.equals(employeeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ResponseFindUserVO findUserVO = modelMapper.map(employeeDTO, ResponseFindUserVO.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(findUserVO);
    }

    @GetMapping("/users/me")
    public ResponseEntity<ResponseFindUserVO> getMyInfo() {
        // 현재 인증된 사용자 정보 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String employeeId = authentication.getName();

        // userId로 유저 조회
        EmployeeDTO userDTO = userService.getUserById(employeeId);

        // VO로 변환 후 응답
        ResponseFindUserVO response = modelMapper.map(userDTO, ResponseFindUserVO.class);
        return ResponseEntity.ok(response);
    }

}
