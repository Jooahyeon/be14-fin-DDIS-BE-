package com.ddis.ddis_hr.member.command.application.service;

import com.ddis.ddis_hr.employee.command.domain.repository.EmployeeRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.EmployeeMfaSecret;
import com.ddis.ddis_hr.member.command.domain.repository.EmployeeMfaSecretRepository;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class OtpProvisionService {

    private final EmployeeRepository employeeRepo;
    private final EmployeeMfaSecretRepository secretRepo;
    private final JavaMailSender mailer;        // ← 주입
    private final TemplateEngine thymeleaf;     // ← 주입
    private final GoogleAuthenticator   gauth = new GoogleAuthenticator();

    /* QR 이미지 bytes 반환 */
    public byte[] qrPng(String uri, int size) {
        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(uri, BarcodeFormat.QR_CODE, size, size);

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                MatrixToImageWriter.writeToStream(matrix, "PNG", out);
                return out.toByteArray();
            }
        } catch (WriterException | IOException e) {
            throw new IllegalStateException("QR 코드 생성 실패", e);
        }
    }

    /* Secret 발급 + otpauth URI */
    public byte[] issueAndMail(Long empId) {

        // ── 1. 사원‧이메일 확인 ─────────────────────────────────────
        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new IllegalArgumentException("사원이 존재하지 않습니다"));
        String email = emp.getEmployeeEmail();
        if (email == null || email.isBlank())
            throw new IllegalStateException("사원 이메일이 등록되어 있지 않습니다");

        // ── 2. 이미 secret 있으면 그대로, 없으면 새로 생성 ──────────
        String secret;
        if (secretRepo.existsById(empId)) {            // 중복 방지
            secret = secretRepo.getReferenceById(empId).getSecretB32();
        } else {
            secret = gauth.createCredentials().getKey();
            secretRepo.save(new EmployeeMfaSecret(empId, secret));
        }

        // ── 3. otpauth URI & QR PNG 생성 ────────────────────────────
        String uri = String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s&digits=6&period=30",
                "DDIS-HR", email, secret, "DDIS-HR");

        byte[] png = qrPng(uri, 240);

        // ── 4. 안내 메일 발송 (QR inline) ───────────────────────────
        sendSetupMail(email, png);

        return png;            // 컨트롤러로 전달
    }
    private void sendSetupMail(String to, byte[] qrPng) {
        try {
            MimeMessage mime = mailer.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(mime, true, "UTF-8");

            // 1) 본문 HTML
            Context ctx = new Context();
            ctx.setVariable("issuer", "DDIS-HR");
            String html = thymeleaf.process("mail/otp-setup.html", ctx);

            // 2) 인라인 이미지
            h.addInline("qrImg", new ByteArrayResource(qrPng), "image/png");

            h.setFrom("noreply@ddis.hr", "DDIS HR");
            h.setTo(to);
            h.setSubject("🔐 DDIS-HR OTP 등록 안내");
            h.setText(html, true);

            mailer.send(mime);
        } catch (Exception e) {
            throw new IllegalStateException("OTP 메일 발송 실패", e);
        }
    }
}
