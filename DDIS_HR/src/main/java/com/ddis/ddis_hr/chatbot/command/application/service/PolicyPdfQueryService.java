package com.ddis.ddis_hr.chatbot.command.application.service;

import com.ddis.ddis_hr.chatbot.command.application.dto.PolicyFileDTO;

import java.util.List;

public interface PolicyPdfQueryService {
    List<PolicyFileDTO> listUploadedPolicyFiles();
}
