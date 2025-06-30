package com.ddis.ddis_hr.chatbot.command.application.service;


import com.ddis.ddis_hr.chatbot.command.application.dto.ChatbotRequestDTO;

public interface ChatbotQueryService {
    ChatbotRequestDTO getChatbotRequestDTO(Long employeeId);
}
