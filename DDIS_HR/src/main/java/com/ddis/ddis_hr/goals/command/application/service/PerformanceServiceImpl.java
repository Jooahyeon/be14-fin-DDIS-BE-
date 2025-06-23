package com.ddis.ddis_hr.goals.command.application.service;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.employee.command.domain.repository.EmployeeRepository;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceRequestDTO;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceResponseDTO;
import com.ddis.ddis_hr.goals.command.domain.aggregate.Goals;
import com.ddis.ddis_hr.goals.command.domain.aggregate.Performance;
import com.ddis.ddis_hr.goals.command.domain.aggregate.SelfreviewFile;
import com.ddis.ddis_hr.goals.command.domain.repository.GoalsRepository;
import com.ddis.ddis_hr.goals.command.domain.repository.PerformanceRepository;
import com.ddis.ddis_hr.goals.command.domain.repository.SelfreviewFileRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    private final S3Service s3Service;
    private final GoalsRepository goalsRepo;
    private final PerformanceRepository perfRepo;
    private final EmployeeRepository employeeRepo;
    private final SelfreviewFileRepository selfreviewFileRepo;

    public PerformanceServiceImpl(S3Service s3Service, GoalsRepository goalsRepo, PerformanceRepository perfRepo, EmployeeRepository employeeRepo, SelfreviewFileRepository selfreviewFileRepo) {
        this.s3Service = s3Service;
        this.goalsRepo = goalsRepo;
        this.perfRepo = perfRepo;
        this.employeeRepo = employeeRepo;
        this.selfreviewFileRepo = selfreviewFileRepo;
    }


    @Transactional
    @Override
    public PerformanceResponseDTO savePerformance(
            Long goalId,
            PerformanceRequestDTO info,
            Long employeeId
    ) {
        // 1) Goals, Employee 조회
        Goals goal = goalsRepo.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Goal: " + goalId));
        Employee selfReviewer = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Employee: " + employeeId));

        // 2) Performance 객체 생성 (attachmentUrl 제거)
        Performance p = Performance.builder()
                .goal(goal)
                .selfReviewer(selfReviewer)
                .performanceValue(info.getPerformanceValue())
                .selfreviewContent(info.getSelfreviewContent())
                .status(Performance.Status.대기)
                .createdAt(LocalDateTime.now())
                .build();

        p = perfRepo.save(p);

        // 3) 파일 메타가 들어왔으면 SelfReviewFile 엔티티로 저장
        if (info.getAttachmentKey() != null && !info.getAttachmentKey().isEmpty()) {
            String downloadUrl = s3Service.generateDownloadUrl(
                    info.getAttachmentKey(),
                    info.getFileType()
            );

            SelfreviewFile fileEntity = SelfreviewFile.builder()
                    .fileName(info.getOriginalFileName())
                    .fileUrl(info.getAttachmentKey())
                    .uploadDate(LocalDateTime.now())
                    .fileSize(info.getFileSize())
                    .fileType(info.getFileType())
                    .employee(selfReviewer)
                    .performance(p)
                    .build();

            selfreviewFileRepo.save(fileEntity);
        }

        // 4) 응답 DTO 생성 (필요한 값만 넘기면 됩니다)
        PerformanceResponseDTO dto = PerformanceResponseDTO.builder()
                .performanceId(p.getId())
                .performanceValue(p.getPerformanceValue().intValue())
                .selfreviewContent(p.getSelfreviewContent())
                .createdAt(p.getCreatedAt())
                .attachmentKey(info.getAttachmentKey())
                .originalFileName(info.getOriginalFileName())
                .fileType(info.getFileType())
                .fileSize(info.getFileSize())
                .build();
        return dto;

    }

    @Override
    public PerformanceResponseDTO updatePerformance(
            Long goalId,
            Long perfId,
            PerformanceRequestDTO info,
            Long employeeId
    ) {
        // 1) Goal과 Performance 엔티티 조회 (goalId, perfId 확인)
        Performance existing = perfRepo.findById(perfId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Performance: " + perfId));
        if (!existing.getGoal().getGoalId().equals(goalId)) {
            throw new IllegalArgumentException("해당 Goal에 속하지 않는 실적입니다.");
        }
        // 2) 사용자(작성자) 검증 로직 등 필요 시 추가

        // 3) 엔티티 필드 업데이트
        existing.setPerformanceValue(info.getPerformanceValue());
        existing.setSelfreviewContent(info.getSelfreviewContent());
        // 첨부파일 수정 로직이 필요하다면 info.getAttachmentKey() 등을 반영

        // 새로운 첨부 키가 제공되었다면
        if (info.getAttachmentKey() != null && !info.getAttachmentKey().isEmpty()) {
            // (선택) 기존 파일 삭제 로직
            // S3 다운로드 URL 생성 (필요시)
            String downloadUrl = s3Service.generateDownloadUrl(
                    info.getAttachmentKey(),
                    info.getFileType()
            );

            // 메타 저장
            SelfreviewFile fileEntity = SelfreviewFile.builder()
                    .fileName(info.getOriginalFileName())
                    .fileUrl(info.getAttachmentKey())
                    .uploadDate(LocalDateTime.now())
                    .fileSize(info.getFileSize())
                    .fileType(info.getFileType())
                    .employee(existing.getSelfReviewer())
                    .performance(existing)
                    .build();
            selfreviewFileRepo.save(fileEntity);
        }

        // 4) 리포지토리에 저장 (JPA는 더티체킹으로 자동 반영)
        Performance saved = perfRepo.save(existing);
        // 5) 응답 DTO 반환
        return PerformanceResponseDTO.builder()
                .performanceId(saved.getId())
                .performanceValue(saved.getPerformanceValue().intValue())
                .selfreviewContent(saved.getSelfreviewContent())
                .createdAt(saved.getCreatedAt())
                .attachmentKey(info.getAttachmentKey())
                .originalFileName(info.getOriginalFileName())
                .fileType(info.getFileType())
                .fileSize(info.getFileSize())
                .build();
    }

    @Override
    public void deletePerformance(Long goalId, Long perfId, Long employeeId) {
        Performance existing = perfRepo.findById(perfId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Performance: " + perfId));
        if (!existing.getGoal().getGoalId().equals(goalId)) {
            throw new IllegalArgumentException("해당 Goal에 속하지 않는 실적입니다.");
        }
        perfRepo.delete(existing);
        // (필요 시 S3 파일 삭제 로직 포함)
    }

}
