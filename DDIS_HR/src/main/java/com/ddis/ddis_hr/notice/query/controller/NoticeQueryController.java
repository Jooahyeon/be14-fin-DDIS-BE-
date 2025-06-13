package com.ddis.ddis_hr.notice.query.controller;

import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.notice.query.dto.NoticeQueryDTO;
import com.ddis.ddis_hr.notice.query.service.NoticeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeQueryController {

    private final NoticeQueryService noticeQueryService;

    @GetMapping("/me")
    public ResponseEntity<List<NoticeQueryDTO>> getNotice(@AuthenticationPrincipal CustomUserDetails user){
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<NoticeQueryDTO> list = noticeQueryService.getByEmployeeId(user.getEmployeeId());
        return ResponseEntity.ok(list);
    }

//    @GetMapping("/{employeeId}")
//    public ResponseEntity<List<NoticeQueryDTO>> getNotice(@PathVariable("employeeId") Long employeeId) {
//        List<NoticeQueryDTO> list = noticeQueryService.getByEmployeeId(employeeId);
//        return ResponseEntity.ok(list);
//    }
}
