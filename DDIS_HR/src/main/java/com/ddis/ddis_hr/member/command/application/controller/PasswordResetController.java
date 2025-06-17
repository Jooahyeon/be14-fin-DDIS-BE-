package com.ddis.ddis_hr.member.command.application.controller;

import com.ddis.ddis_hr.member.command.application.service.OtpProvisionService;
import com.ddis.ddis_hr.member.command.application.service.OtpVerifyService;
import com.ddis.ddis_hr.member.command.application.service.PasswordResetService;
import com.ddis.ddis_hr.member.command.domain.repository.EmployeeMfaSecretRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {
    private final PasswordResetService svc;
    private final OtpProvisionService otpProvisionService;
    private final OtpVerifyService otpVerifyService;
    private final EmployeeMfaSecretRepository secretRepo;

    /* 1) 발급 */
    @PostMapping("/request")
    public ResponseEntity<TokenResp> request(@RequestBody Req dto) {
        UUID tokenId = svc.issueCode(dto.employeeId(), dto.email());
        return ResponseEntity.ok(new TokenResp(tokenId));
    }

    /* 2) 검증 */
    @PostMapping("/verify")
    public ResponseEntity<Void> verify(@RequestBody Ver dto) {
        svc.verifyCode(dto.tokenId(), dto.code());
        return ResponseEntity.noContent().build();   // 204 → 프론트는 비번 변경 화면으로
    }

    /* 3) 비밀번호 변경 */
    @PostMapping("/change-password")
    public ResponseEntity<Void> change(@RequestBody PwDto dto) {
        svc.changePassword(dto.employeeId(), dto.newPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value="/mfa/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] qr(@RequestParam Long empId) {
        return otpProvisionService.issueAndMail(empId);          // ZXing or https://github.com/kenglxn/QRGen
    }

    @PostMapping("/mfa/verify")
    public ResponseEntity<Void> verifyOtp(@RequestBody OtpDto dto) {
        otpVerifyService.verify(dto.employeeId(), dto.code());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mfa/exists")
    public ResponseEntity<Void> exists(@RequestParam Long empId) {
        boolean ok = secretRepo.existsById(empId);
        return ok ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    /* ── DTO ── */
    record Req(Long employeeId, String email) {}
    record Ver(UUID tokenId, String code) {}
    record PwDto(Long employeeId, String newPassword) {}
    public record OtpDto(Long employeeId, int code) {}
    record TokenResp(UUID tokenId) {}

}
