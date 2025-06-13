package com.ddis.ddis_hr.S3Config.controller;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    /**
     * 클라이언트가 S3에 업로드할 수 있도록
     * presigned PUT URL + S3에 저장할 key를 함께 돌려줍니다.
     */
    @GetMapping("/upload-url")
    public ResponseEntity<Map<String, String>> getUploadUrl(
            @RequestParam("filename")    String originalFilename,
            @RequestParam("contentType") String contentType
    ) {
        // 1) 키 생성 (예: boards/{timestamp}-{uuid}_{원본파일명})
        String key = generateKey(originalFilename);

        // 2) presigned PUT URL 발급
        String url = s3Service.generateUploadUrl(key, contentType);

        // 3) key + url 리턴
        return ResponseEntity.ok(Map.of(
                "key", key,
                "url", url
        ));
    }

    private String generateKey(String originalFilename) {
        String timePart = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        );
        String uuidPart = UUID.randomUUID().toString();
        return String.format("boards/%s-%s_%s",
                timePart, uuidPart, originalFilename
        );
    }

    /**
     * 클라이언트가 다운로드할 수 있도록 presigned GET URL을 발급
     */
     @GetMapping("/download-url")
     public ResponseEntity<String> getDownloadUrl(
                @RequestParam("filename")    String key,
                @RequestParam("contentType") String contentType
     ){
             return ResponseEntity.ok(s3Service.generateDownloadUrl(key, contentType));
     }

    /**
     * 서버가 직접 S3에서 파일을 삭제
     */
     @DeleteMapping("/file")
     public ResponseEntity<String> deleteFile(
                @RequestParam("filename")    String key,
                @RequestParam("contentType") String contentType
     ) {
             s3Service.deleteFile(key, contentType);
             return ResponseEntity.ok("삭제 완료: " + key);
     }
}