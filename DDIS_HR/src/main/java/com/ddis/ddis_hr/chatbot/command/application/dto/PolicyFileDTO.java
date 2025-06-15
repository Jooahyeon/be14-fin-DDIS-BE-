package com.ddis.ddis_hr.chatbot.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyFileDTO {
    private String key;        // S3 전체 경로
    private String fileName;   // 실제 파일 이름
    private String downloadUrl; // (선택) presigned URL
}