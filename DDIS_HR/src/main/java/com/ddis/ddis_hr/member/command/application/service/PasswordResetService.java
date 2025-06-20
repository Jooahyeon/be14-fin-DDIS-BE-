package com.ddis.ddis_hr.member.command.application.service;



import com.ddis.ddis_hr.member.command.application.controller.PasswordResetController;

import java.util.UUID;

public interface PasswordResetService {
    /** 토큰 발급 & 메일 전송 후 tokenId 리턴 */
    UUID issueCode(Long employeeId, String email);

    /** 사용자 입력 코드 검증 (실패 시 예외) */
    void verifyCode(UUID tokenId, String inputCode);

    void changePassword(Long employeeId, String newPw);
}
