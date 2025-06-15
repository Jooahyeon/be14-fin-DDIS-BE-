package com.ddis.ddis_hr.chatbot.command.application.service;

import com.ddis.ddis_hr.chatbot.command.application.dto.UploadPolicyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.lowagie.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextRenderer;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PolicyPdfUploadServiceImpl implements PolicyPdfUploadService {

    private final TemplateEngine templateEngine;
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public String generateAndUpload(UploadPolicyDTO uploadPolicyDTO) throws IOException {
        Map<String, Object> data = Map.of(
                "title", uploadPolicyDTO.getTitle(),
                "date", LocalDate.now().toString(),
                "content", uploadPolicyDTO.getTitle() + "에 대한 설명...",
                "tags", uploadPolicyDTO.getTags()
        );

        String html = renderHtml(uploadPolicyDTO.getTemplate(), data);
        File pdf = generatePdf(html);

        String date = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String s3Key = String.format("company-policy/%s/%s_%s.pdf",
                uploadPolicyDTO.getCategory(),
                uploadPolicyDTO.getTemplate().replace(".html", ""),
                date);

        uploadToS3(pdf, s3Key);
        return s3Key;
    }

    private String renderHtml(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process("policy/" + templateName, context);
    }

    private File generatePdf(String htmlContent) throws IOException {
        File tempFile = File.createTempFile("policy-", ".pdf");

        try (OutputStream os = new FileOutputStream(tempFile)) {
            ITextRenderer renderer = new ITextRenderer();

            // 안전한 클래스패스 기반 폰트 경로 로딩
            String fontPath = Objects.requireNonNull(getClass().getClassLoader()
                            .getResource("fonts/NanumGothic.ttf"))
                    .toExternalForm();

            renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            // base URL 지정 (상대 경로 이미지/CSS 대응)
            URL baseUrl = getClass().getResource("/templates/");
            renderer.setDocumentFromString(htmlContent, baseUrl != null ? baseUrl.toExternalForm() : null);

            renderer.layout();
            renderer.createPDF(os);
        }

        return tempFile;
    }

    private void uploadToS3(File file, String s3Key) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(s3Key)
                        .contentType("application/pdf")
                        .build(),
                RequestBody.fromFile(file)
        );
    }
}
