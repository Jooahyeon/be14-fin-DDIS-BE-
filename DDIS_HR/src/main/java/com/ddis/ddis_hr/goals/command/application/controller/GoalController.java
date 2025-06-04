package com.ddis.ddis_hr.goals.command.application.controller;

import com.ddis.ddis_hr.goals.command.application.dto.GoalDTO;
import com.ddis.ddis_hr.goals.command.application.dto.GoalInsertDTO;
import com.ddis.ddis_hr.goals.command.application.service.GoalsService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    private final GoalsService goalsService;

    @Autowired
    public GoalController( GoalsService goalsService){
        this.goalsService = goalsService;
    }


    @GetMapping
    public ResponseEntity<List<GoalDTO>> getGoals( @AuthenticationPrincipal CustomUserDetails user) {
        List<GoalDTO> goals = goalsService.getGoals(user.getEmployeeId());
        return ResponseEntity.ok(goals);
    }

    @PostMapping
    public ResponseEntity<Long> createGoal(@RequestBody GoalInsertDTO req,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        req.setEmployeeId(user.getEmployeeId());  // 요청 DTO에 넣어주기
        Long id = goalsService.createGoal(req);
        return ResponseEntity.ok(id);
    }
}
