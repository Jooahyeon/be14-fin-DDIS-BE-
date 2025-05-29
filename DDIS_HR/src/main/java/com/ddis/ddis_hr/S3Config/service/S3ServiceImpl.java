package com.ddis.ddis_hr.S3Config.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.*;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;           // 파일 삭제 등에 사용
    private final S3Presigner s3Presigner;     // presigned URL 발급에 사용

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /* presigned URL을 발급해서 클라이언트가 직접 S3에 PUT 요청하여 업로드할 수 있게 함 */
    @Override
    public String generateUploadUrl(String fileName,String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)  // 클라이언트가 어떤 파일이든 업로드 가능
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))  // 10분간 유효
                .build();

        PresignedPutObjectRequest presignedUrl = s3Presigner.presignPutObject(presignRequest);
        return presignedUrl.url().toString();
    }

    /* 다운로드용 presigned URL 발급 */
    @Override
    public String generateDownloadUrl(String fileName, String contentType) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))  // 10분 유효
                .build();

        PresignedGetObjectRequest presignedUrl = s3Presigner.presignGetObject(presignRequest);
        return presignedUrl.url().toString();
    }

    /* S3 객체 삭제 */
    @Override
    public void deleteFile(String fileName,String contentType) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3Client.deleteObject(deleteRequest);
    }
}
