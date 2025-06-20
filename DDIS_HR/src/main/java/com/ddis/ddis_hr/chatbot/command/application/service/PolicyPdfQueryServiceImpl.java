package com.ddis.ddis_hr.chatbot.command.application.service;

import com.ddis.ddis_hr.chatbot.command.application.dto.PolicyFileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyPdfQueryServiceImpl implements PolicyPdfQueryService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public List<PolicyFileDTO> listUploadedPolicyFiles() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix("company-policy/")
                .build();

        return s3Client.listObjectsV2(request)
                .contents()
                .stream()
                .map(obj -> {
                    String key = obj.key();
                    String fileName = key.substring(key.lastIndexOf('/') + 1);

                    // presigned URL 생성
                    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build();

                    GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                            .getObjectRequest(getObjectRequest)
                            .signatureDuration(Duration.ofMinutes(30)) // 유효시간 30분
                            .build();

                    String downloadUrl = s3Presigner.presignGetObject(presignRequest)
                            .url()
                            .toString();

                    return new PolicyFileDTO(key, fileName, downloadUrl);
                })
                .collect(Collectors.toList());
    }
}
