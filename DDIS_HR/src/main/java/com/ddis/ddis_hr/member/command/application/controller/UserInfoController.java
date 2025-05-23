package com.ddis.ddis_hr.member.command.application.controller;

import com.samsung.dieat.member.command.application.dto.UserInfoDTO;
import com.samsung.dieat.member.command.application.service.UserInfoService;
import com.samsung.dieat.member.command.domain.aggregate.entity.UserInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserInfoController {
    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/member/{userCode}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable("userCode") int userCode) {
        return ResponseEntity.ok(userInfoService.getUserInfoById(userCode));
    }

}
