package com.ddis.ddis_hr.payroll.command.application.service;

import com.ddis.ddis_hr.payroll.command.application.dto.RetirementMailRequestDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetirementMailServiceImpl implements RetirementMailService {

    private final JavaMailSender mailSender;
    private final RetirementPdfGenerator pdfGenerator;

    @Override
    public void sendRetirementByEmail(RetirementMailRequestDTO dto) {
        try {
            // PDF 생성
            byte[] pdfBytes = pdfGenerator.generate(dto);

            // 메일 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(dto.getEmployeeEmail());

            // 제목
            String subject = String.format("[DDIS] 퇴직금 명세서 – %s님", dto.getEmployeeName());
            helper.setSubject(subject);

            // 본문
            String body = String.format(
                    "안녕하세요 %s님,\n\n" +
                            "퇴직금 명세서를 첨부하여 보내드립니다.\n\n" +
                            "내용을 확인해주시고, 문의사항이 있으시면 인사팀에 연락주시기 바랍니다.\n\n" +
                            "감사합니다.\n\n" +
                            "- DDIS 인사팀 드림 -",
                    dto.getEmployeeName()
            );
            helper.setText(body);

            // 파일 이름
            String fileName = String.format("%s_퇴직금명세서.pdf", dto.getEmployeeName());
            helper.addAttachment(fileName, new ByteArrayResource(pdfBytes));

            // 전송
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("퇴직금 메일 전송 실패", e);
        }
    }
}
