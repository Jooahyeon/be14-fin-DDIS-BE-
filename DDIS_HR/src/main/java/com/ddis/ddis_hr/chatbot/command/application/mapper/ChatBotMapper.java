package com.ddis.ddis_hr.chatbot.command.application.mapper;

import com.ddis.ddis_hr.chatbot.command.application.dto.ChatbotRequestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatBotMapper {

    ChatbotRequestDTO findChatbotRequestDTO(Long employeeId);
}
