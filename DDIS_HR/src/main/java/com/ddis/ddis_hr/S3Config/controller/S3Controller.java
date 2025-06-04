package com.ddis.ddis_hr.S3Config.controller;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    /**
     * 클라이언트가 업로드할 수 있도록 presigned PUT URL을 발급
     * 클라이언트는 이 URL에 파일을 직접 PUT 요청하여 업로드
     */
    @GetMapping("/upload-url")
    public ResponseEntity<String> getUploadUrl(@RequestParam String fileName,@RequestParam String ContentType) {
        return ResponseEntity.ok(s3Service.generateUploadUrl(fileName, ContentType));
    }

    /**
     * 클라이언트가 다운로드할 수 있도록 presigned GET URL을 발급
     */
    @GetMapping("/download-url")
    public ResponseEntity<String> getDownloadUrl(@RequestParam String fileName,@RequestParam String ContentType) {
        return ResponseEntity.ok(s3Service.generateDownloadUrl(fileName,ContentType));
    }

    /**
     * 서버가 직접 S3에서 파일을 삭제
     */
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName, @RequestParam String ContentType) {
        s3Service.deleteFile(fileName, ContentType);
        return ResponseEntity.ok("삭제 완료: " + fileName);
    }
}