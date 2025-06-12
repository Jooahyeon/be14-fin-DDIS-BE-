    package com.ddis.ddis_hr.S3Config.service;

    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;
    import java.nio.charset.StandardCharsets;
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
            // ① 실제 다운로드 받을 때 사용할 파일명만 추출
            String actualFileName = fileName.contains("/")
                    ? fileName.substring(fileName.lastIndexOf("/") + 1)
                    : fileName;

            // ② percent‐encode 된 UTF-8 한글 파일명 (예: “목표.png” → “%EB%AA%A9%ED%91%9C.png”)
            String encodedName = java.net.URLEncoder.encode(actualFileName, StandardCharsets.UTF_8);

            // ③ RFC5987 형식으로 Content-Disposition 헤더 값 생성
            String disposition = "attachment; filename*=UTF-8''" + encodedName;
            //    → 예시: "attachment; filename*=UTF-8''%EB%AA%A9%ED%91%9C.png"

            // ④ GetObjectRequest 에 미리 responseContentDisposition 지정
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .responseContentDisposition(disposition)
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
