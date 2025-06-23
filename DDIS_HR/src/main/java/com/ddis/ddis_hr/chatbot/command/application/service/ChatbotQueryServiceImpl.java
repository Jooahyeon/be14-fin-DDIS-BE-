package com.ddis.ddis_hr.chatbot.command.application.service;

import com.ddis.ddis_hr.chatbot.command.application.dto.ChatbotRequestDTO;
import com.ddis.ddis_hr.chatbot.command.application.mapper.ChatBotMapper;
import org.springframework.stereotype.Service;

@Service
public class ChatbotQueryServiceImpl implements ChatbotQueryService {

    private final ChatBotMapper chatBotMapper;

    public ChatbotQueryServiceImpl(ChatBotMapper chatBotMapper) {
        this.chatBotMapper = chatBotMapper;
    }

    @Override
    public ChatbotRequestDTO getChatbotRequestDTO(Long employeeId) {
        return chatBotMapper.findChatbotRequestDTO(employeeId);
    }
}
