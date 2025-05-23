package com.ddis.ddis_hr.member.command.application.controller;

import com.samsung.dieat.member.command.application.dto.UserDTO;
import com.samsung.dieat.member.command.application.service.UserService;
import com.samsung.dieat.member.command.domain.aggregate.vo.RequestRegistUserVO;
import com.samsung.dieat.member.command.domain.aggregate.vo.ResponseFindUserVO;
import com.samsung.dieat.member.command.domain.aggregate.vo.ResponseRegistUserVO;
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

    @Value("${admin.secret}")
    private String adminSecretCode;

    @Autowired
    public UserController(Environment env, UserService userService, ModelMapper modelMapper) {
        this.env = env;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/health")
    public String status(){
        return "살 빼!!!!" + env.getProperty("local.server.port");
    }

    @PostMapping("users")
    public ResponseEntity<ResponseRegistUserVO> registUser(@RequestBody RequestRegistUserVO newUser) {
        log.info("adminSecretCode: '{}'", adminSecretCode);
        log.info("inviteCode: '{}'", newUser.getInviteCode());

        UserDTO userDTO = modelMapper.map(newUser, UserDTO.class);


        if(newUser.getInviteCode() != null && newUser.getInviteCode().trim().equals(adminSecretCode.trim())){
            userDTO.setRole("ADMIN");
        } else {
            userDTO.setRole("USER");
        }


        userService.registUser(userDTO);
        ResponseRegistUserVO successRegistUser = modelMapper.map(userDTO, ResponseRegistUserVO.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(successRegistUser);



    }

    @GetMapping("/users/{memNo}")
    public ResponseEntity<ResponseFindUserVO> getUsers(@PathVariable String memNo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserId = auth.getName();

        UserDTO userDTO = userService.getUserById(memNo);

        if (!loggedInUserId.equals(userDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ResponseFindUserVO findUserVO = modelMapper.map(userDTO, ResponseFindUserVO.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(findUserVO);
    }
}
