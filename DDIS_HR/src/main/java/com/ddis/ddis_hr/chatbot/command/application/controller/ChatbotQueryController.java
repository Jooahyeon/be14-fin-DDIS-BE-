package com.ddis.ddis_hr.chatbot.command.application.controller;

import com.ddis.ddis_hr.chatbot.command.application.dto.ChatbotRequestDTO;
import com.ddis.ddis_hr.chatbot.command.application.service.ChatbotQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
@RequiredArgsConstructor
public class ChatbotQueryController {

    private final ChatbotQueryService chatbotQueryService;


    @GetMapping
    public ResponseEntity<ChatbotRequestDTO> query(@AuthenticationPrincipal CustomUserDetails user) {
        Long employeeId = user.getEmployeeId();
        return ResponseEntity.ok(chatbotQueryService.getChatbotRequestDTO(employeeId));
    }
}
