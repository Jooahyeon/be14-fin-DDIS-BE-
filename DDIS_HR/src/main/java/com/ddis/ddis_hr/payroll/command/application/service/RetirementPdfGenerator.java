package com.ddis.ddis_hr.payroll.command.application.service;

import com.ddis.ddis_hr.payroll.command.application.dto.RetirementMailRequestDTO;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RetirementPdfGenerator {

    private final TemplateEngine templateEngine;

    public byte[] generate(RetirementMailRequestDTO dto) {
        Context context = new Context();
        context.setVariable("data", dto); // DTO 자체를 넘김

        String html = templateEngine.process("payroll/retirement", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();

            String fontPath = Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("fonts/NanumGothic.ttf")).toExternalForm();
            renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            String baseUrl = Objects.requireNonNull(getClass().getResource("/templates/")).toExternalForm();
            renderer.setDocumentFromString(html, baseUrl);

            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("퇴직금 PDF 생성 실패", e);
        }
    }

}
