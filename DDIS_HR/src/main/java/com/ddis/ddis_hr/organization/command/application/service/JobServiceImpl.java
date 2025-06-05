package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.JobRequestDTO;
import com.ddis.ddis_hr.organization.command.application.dto.JobResponseDTO;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.JobEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

    private final JobsRepository jobsRepository;

    @Autowired
    public JobServiceImpl(JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    @Override
    public JobResponseDTO createJob(JobRequestDTO request) {
        // 1) 엔티티로 변환
        JobEntity job = JobEntity.builder()
                .jobName(request.getJobName())
                .jobCode(request.getJobCode())
                .jobRole(request.getJobRole())
                .jobNeed(request.getJobNeed())
                .jobNecessary(request.getJobNecessary())
                .jobPreference(request.getJobPreference())
                .teamId(request.getTeamId())
                .build();

        // 2) 저장
        JobEntity saved = jobsRepository.save(job);

        // 3) DTO로 변환 후 반환
        return mapToResponseDTO(saved);
    }

    @Override
    public JobResponseDTO updateJob(Long jobId, JobRequestDTO request) {
        JobEntity existing = jobsRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 직무를 찾을 수 없습니다: " + jobId));

        // 필드 수정
        existing.setJobName(request.getJobName());
        existing.setJobCode(request.getJobCode());
        existing.setJobRole(request.getJobRole());
        existing.setJobNeed(request.getJobNeed());
        existing.setJobNecessary(request.getJobNecessary());
        existing.setJobPreference(request.getJobPreference());
        existing.setTeamId(request.getTeamId());

        // JPA의 변경 감지(dirty checking)에 의해 트랜잭션 커밋 시 업데이트됨
        JobEntity updated = jobsRepository.save(existing);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteJob(Long jobId) {
        JobEntity existing = jobsRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 직무를 찾을 수 없습니다: " + jobId));
        jobsRepository.delete(existing);
    }

    // Entity → DTO 매핑 헬퍼 메서드
    private JobResponseDTO mapToResponseDTO(JobEntity job) {
        return JobResponseDTO.builder()
                .id(job.getJobId())
                .jobName(job.getJobName())
                .jobCode(job.getJobCode())
                .jobRole(job.getJobRole())
                .jobNeed(job.getJobNeed())
                .jobNecessary(job.getJobNecessary())
                .jobPreference(job.getJobPreference())
                .teamId(job.getTeamId())
                .build();
    }
}
