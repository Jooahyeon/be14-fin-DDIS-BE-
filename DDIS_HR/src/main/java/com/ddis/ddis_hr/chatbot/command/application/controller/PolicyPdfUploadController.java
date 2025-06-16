package com.ddis.ddis_hr.chatbot.command.application.controller;

import com.ddis.ddis_hr.chatbot.command.application.dto.UploadPolicyDTO;
import com.ddis.ddis_hr.chatbot.command.application.service.PolicyPdfUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docs")
public class PolicyPdfUploadController {

    private final PolicyPdfUploadService policyPdfUploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody UploadPolicyDTO uploadPolicyDTO) throws IOException {
        String uploadedKey = policyPdfUploadService.generateAndUpload(uploadPolicyDTO);
        return ResponseEntity.ok("업로드 완료: " + uploadedKey);
    }

    @PostMapping("/batch-upload")
    public ResponseEntity<List<String>> batchUpload(@RequestBody List<UploadPolicyDTO> dtos) throws IOException {
        List<String> uploadedKeys = new ArrayList<>();
        for (UploadPolicyDTO dto : dtos) {
            String key = policyPdfUploadService.generateAndUpload(dto);
            uploadedKeys.add(key);
        }
        return ResponseEntity.ok(uploadedKeys);
    }
}