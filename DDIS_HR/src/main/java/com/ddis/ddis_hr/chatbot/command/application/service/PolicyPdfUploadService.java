package com.ddis.ddis_hr.chatbot.command.application.service;

import com.ddis.ddis_hr.chatbot.command.application.dto.UploadPolicyDTO;

import java.io.IOException;

public interface PolicyPdfUploadService {
    String generateAndUpload(UploadPolicyDTO uploadPolicyDTO) throws IOException;
}
