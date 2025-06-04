package com.ddis.ddis_hr.S3Config.service;

public interface S3Service {

    // 업로드를 위한 presigned URL 생성 (클라이언트에서 PUT 요청 사용)
    String generateUploadUrl(String fileName, String contentType);

    // 다운로드를 위한 presigned URL 생성 (클라이언트에서 GET 요청 사용)
    String generateDownloadUrl(String fileName,String contentType);

    // 실제 S3에서 파일 삭제
    void deleteFile(String fileName,String contentType);
}
