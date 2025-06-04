package com.ddis.ddis_hr.goals.command.application.controller;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceRequestDTO;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceResponseDTO;
import com.ddis.ddis_hr.goals.command.application.service.PerformanceService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/goalsperf")
public class PerformanceController {

    private final PerformanceService perfService;
    private final S3Service s3Service;

    public PerformanceController(PerformanceService perfService, S3Service s3Service) {
        this.perfService = perfService;
        this.s3Service = s3Service;

    }



    /**
     * 프론트에서 요청할 때, S3 presigned GET URL을 생성해서 그대로 전달
     * @param fileKey  S3에 저장된 객체 키 (예: "performance/uuid-파일이름.ext")
     */
    @GetMapping("/attachment/download-url")
    public ResponseEntity<String> getAttachmentDownloadUrl(@RequestParam String fileKey) {
        // contentType은 일반적으로 S3 PUT 때 저장된 메타를 알고 있으면, 여기서는 생략 가능
        String presignedUrl = s3Service.generateDownloadUrl(fileKey, "");
        return ResponseEntity.ok(presignedUrl);
    }

    @PostMapping("/{goalId}/performance")
    public ResponseEntity<?> uploadPerformance(
            @PathVariable Long goalId,
            @RequestBody PerformanceRequestDTO info,
            @AuthenticationPrincipal CustomUserDetails user

    ){
        PerformanceResponseDTO saved = perfService.savePerformance(goalId, info, user.getEmployeeId());

        return ResponseEntity.ok(saved);

    }

    // 2) 수정(업데이트)용 PUT 매핑 추가
    @PutMapping("/{goalId}/performance/{perfId}")
    public ResponseEntity<PerformanceResponseDTO> updatePerformance(
            @PathVariable Long goalId,
            @PathVariable Long perfId,
            @RequestBody PerformanceRequestDTO info,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        // perfService.updatePerformance 내부에서 perfId를 이용해 기존 엔티티를 찾아 수정하도록 구현
        PerformanceResponseDTO dto = perfService.updatePerformance(
                goalId,
                perfId,
                info,
                user.getEmployeeId()
        );
        return ResponseEntity.ok(dto);
    }

    // 3) 삭제(DELETE)용 매핑
    @DeleteMapping("/{goalId}/performance/{perfId}")
    public ResponseEntity<Void> deletePerformance(
            @PathVariable Long goalId,
            @PathVariable Long perfId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        perfService.deletePerformance(goalId, perfId, user.getEmployeeId());
        return ResponseEntity.noContent().build();
    }
}
