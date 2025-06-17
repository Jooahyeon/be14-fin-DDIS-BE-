package com.ddis.ddis_hr.member.command.application.service;

import com.ddis.ddis_hr.employee.command.domain.repository.EmployeeRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.PwResetToken;
import com.ddis.ddis_hr.member.command.domain.repository.PwResetTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetServiceImpl implements PasswordResetService {
    private final PwResetTokenRepository tokenRepo;
    private final EmployeeRepository employeeRepo;         // 기존 사원 테이블
    private final JavaMailSender mailer;
    private final PasswordEncoder encoder;              // BCrypt

    private static final Duration TTL = Duration.ofMinutes(10);

    @Override                                   /* ① 발급 + 메일 */
    public UUID issueCode(Long empId, String email) {

        // 1) 사번 존재 확인
        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new IllegalArgumentException("사원이 존재하지 않습니다"));

        // 2) 이메일 일치 여부
        if (!email.equalsIgnoreCase(emp.getEmployeeEmail())) {   // 필드명은 실제 컬럼에 맞게
            throw new IllegalArgumentException("등록된 이메일과 일치하지 않습니다");
        }

        String code = "%06d".formatted(ThreadLocalRandom.current().nextInt(1_000_000));
        String hash = DigestUtils.sha256Hex(code);

        PwResetToken tok = PwResetToken.issue(empId, hash, TTL);
        tokenRepo.save(tok);

        send(email, code, tok.getExpiresAt());
        return tok.getTokenId();
    }

    @Override                                   /* ② 코드 검증 */
    public void verifyCode(UUID tokenId, String input) {
        PwResetToken t = tokenRepo.findValid(tokenId)
                .orElseThrow(() -> new IllegalStateException("만료/존재X"));

        boolean ok = MessageDigest.isEqual(
                t.getCodeHash().getBytes(StandardCharsets.UTF_8),
                DigestUtils.sha256Hex(input).getBytes(StandardCharsets.UTF_8));
        if (!ok) throw new IllegalArgumentException("코드 불일치");

        t.setConsumedAt(LocalDateTime.now());   // 1회용 처리
    }

    @Override                                   /* ③ 비밀번호 변경 */
    public void changePassword(Long empId, String rawPw) {
        Employee e = employeeRepo.findById(empId)
                .orElseThrow(() -> new IllegalStateException("사원 없음"));
        e.setEmployeePwd(encoder.encode(rawPw));
    }

    @Autowired
    TemplateEngine thymeleaf;

    private void send(String to, String code, LocalDateTime exp){
        try {
            Context ctx = new Context();
            ctx.setVariable("code", code);
            ctx.setVariable("expires", exp);

            String html = thymeleaf.process("mail/reset-code.html", ctx);


            MimeMessage mime = mailer.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(mime, true, "UTF-8");
            ClassPathResource logo = new ClassPathResource("templates/mail/pizza.png"); // 56×56 PNG
            h.addInline("logoImg", logo);

            h.setFrom("jerry20011214@gmail.com", "DDIS HR");   // ← 여기서 발생 가능
            h.setTo(to);
            h.setSubject("🛡️ 비밀번호 재설정 인증코드");
            h.setText(html, true);
            mailer.send(mime);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new IllegalStateException("메일 발송 실패", e);
        }
    }
}
