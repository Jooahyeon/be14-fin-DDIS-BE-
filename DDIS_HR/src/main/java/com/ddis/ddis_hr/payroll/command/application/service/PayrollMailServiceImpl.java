package com.ddis.ddis_hr.payroll.command.application.service;

import com.ddis.ddis_hr.payroll.command.application.dto.PayrollMailRequestDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayrollMailServiceImpl implements PayrollMailService {

    private final JavaMailSender mailSender;
    private final PayStubPdfGenerator pdfGenerator;

    @Override
    public void sendPaystubByEmail(PayrollMailRequestDTO dto) {
        try {

            LocalDate salaryDate = LocalDate.parse(dto.getSalaryDate()); // "2024-05-31"
            String formattedSalaryDate = salaryDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
            String workMonth = salaryDate.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy년 MM월"));


            // PDF 생성
            Map<String, Object> data = new HashMap<>();
            data.put("employeeId", dto.getEmployeeId());
            data.put("employeeName", dto.getEmployeeName());
            data.put("headName", dto.getHeadName());
            data.put("departmentName", dto.getDepartmentName());
            data.put("teamName", dto.getTeamName());
            data.put("rankName", dto.getRankName());
            data.put("salaryDate", dto.getSalaryDate());
            data.put("workMonth", workMonth);
            data.put("netSalary", dto.getNetSalary());
            data.put("pays", dto.getPays());
            data.put("deductions", dto.getDeductions());

            byte[] pdfBytes = pdfGenerator.generate(data);

            // 메일 전송
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(dto.getEmployeeEmail());

            // 제목 설정
            String subject = String.format("[DDIS] %s 급여명세서 발송", workMonth);
            helper.setSubject(subject);

            // 본문 설정
            String body = String.format(
                    "안녕하세요 %s님,\n\n" +
                            "%s 급여명세서를 첨부하여 보내드립니다.\n\n" +
                            "첨부된 명세서를 확인해주시기 바라며,\n" +
                            "급여에 관련된 문의사항은 인사팀으로 연락 주시기 바랍니다.\n\n" +
                            "감사합니다.\n\n" +
                            "- DDIS 인사팀 드림 -",
                    dto.getEmployeeName(),
                    workMonth
            );
            helper.setText(body);

            // 파일명 설정
            String fileName = String.format("%s_%s_급여명세서.pdf",
                    dto.getEmployeeName(),
                    workMonth
            );
            helper.addAttachment(fileName, new ByteArrayResource(pdfBytes));

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }
}
