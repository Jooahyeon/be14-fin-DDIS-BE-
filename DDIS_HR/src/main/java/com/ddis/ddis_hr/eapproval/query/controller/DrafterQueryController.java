package com.ddis.ddis_hr.eapproval.query.controller;

import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
import com.ddis.ddis_hr.eapproval.query.service.DrafterQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drafter")
public class DrafterQueryController {

    private final DrafterQueryService userService;

    @GetMapping("/me")
    public ResponseEntity<FindDrafterQueryDTO> getMyInfo(@RequestHeader("Authorization") String token) {
        FindDrafterQueryDTO userInfo = userService.getfindDrafterInfo(token);
        System.out.println("💡 userInfo: " + userInfo); // 혹은 log.info()
        return ResponseEntity.ok(userInfo);
    }
}
