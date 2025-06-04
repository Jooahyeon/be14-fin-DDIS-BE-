package com.ddis.ddis_hr.payroll.command.application.service;

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
public class PayStubPdfGenerator {
    private final TemplateEngine templateEngine;

    public byte[] generate(Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        String html = templateEngine.process("payroll/paystub", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();

            String fontPath = Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("fonts/NanumGothic.ttf")).toExternalForm();

            renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            // 📌 base URL 지정해서 css @font-face도 제대로 인식되도록 처리
            String baseUrl = Objects.requireNonNull(getClass().getResource("/templates/")).toExternalForm();
            renderer.setDocumentFromString(html, baseUrl); // 여기 변경
//            System.out.println("📄 HTML 원문 출력:\n" + html);

            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF 생성 실패", e);
        }
    }
}
