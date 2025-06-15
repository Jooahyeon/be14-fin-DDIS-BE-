package com.ddis.ddis_hr.chatbot.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadPolicyDTO {
    private String template;     // 예: personnel_policy.html
    private String title;        // 예: 인사규정
    private String category;     // 예: 1-personnel
    private List<String> tags;
}
