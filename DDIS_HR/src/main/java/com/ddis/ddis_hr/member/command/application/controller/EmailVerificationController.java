package com.ddis.ddis_hr.member.command.application.controller;

import com.samsung.dieat.member.command.application.dto.EmailSendRequestDTO;
import com.samsung.dieat.member.command.application.dto.EmailVerificationRequestDTO;
import com.samsung.dieat.member.command.application.dto.EmailVerificationResponseDTO;
import com.samsung.dieat.member.command.application.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email-verification")
public class EmailVerificationController {
    private final EmailVerificationService emailVerificationService;

    // 이메일 전송 요청
    @PostMapping("/send")
    public ResponseEntity<EmailVerificationResponseDTO> sendVerification(@RequestBody EmailSendRequestDTO request) {
        emailVerificationService.sendVerificationEmail(request.getEmail());
        EmailVerificationResponseDTO response = new EmailVerificationResponseDTO();
        response.setMessage("인증 코드 전송 완료");
        return ResponseEntity.ok(response);
    }

    // 인증 코드 검증 요청
    @PostMapping("/verify")
    public ResponseEntity<EmailVerificationResponseDTO> verifyCode(@RequestBody EmailVerificationRequestDTO request) {
        boolean verified = emailVerificationService.verifyEmailCode(request.getEmail(), request.getVerificationCode());
        EmailVerificationResponseDTO response = new EmailVerificationResponseDTO();
        if (verified) {
            response.setMessage("이메일 인증 성공");
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("인증 코드 불일치");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
