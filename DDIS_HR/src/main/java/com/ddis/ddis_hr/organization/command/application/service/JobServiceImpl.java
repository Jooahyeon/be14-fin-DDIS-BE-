package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.JobRequestDTO;
import com.ddis.ddis_hr.organization.command.application.dto.JobResponseDTO;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.JobEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.JobsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobsRepository jobsRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public JobServiceImpl(JobsRepository jobsRepository, ObjectMapper objectMapper) {
        this.jobsRepository = jobsRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public JobResponseDTO createJob(JobRequestDTO request) {
        JobEntity job;
        try {
            job = JobEntity.builder()
                    .jobName(request.getJobName())
                    .jobCode(request.getJobCode())
                    .jobRole(objectMapper.writeValueAsString(request.getJobRole()))
                    .jobNeed(objectMapper.writeValueAsString(request.getJobNeed()))
                    .jobNecessary(objectMapper.writeValueAsString(request.getJobNecessary()))
                    .jobPreference(objectMapper.writeValueAsString(request.getJobPreference()))
                    .teamId(request.getTeamId())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패 (createJob)", e);
        }

        JobEntity saved = jobsRepository.save(job);
        return mapToResponseDTO(saved);
    }

    @Override
    public JobResponseDTO updateJob(Long jobId, JobRequestDTO request) {
        JobEntity existing = jobsRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 직무를 찾을 수 없습니다: " + jobId));

        try {
            existing.setJobName(request.getJobName());
            existing.setJobCode(request.getJobCode());
            existing.setJobRole(objectMapper.writeValueAsString(request.getJobRole()));
            existing.setJobNeed(objectMapper.writeValueAsString(request.getJobNeed()));
            existing.setJobNecessary(objectMapper.writeValueAsString(request.getJobNecessary()));
            existing.setJobPreference(objectMapper.writeValueAsString(request.getJobPreference()));
//            existing.setTeamId(request.getTeamId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패 (updateJob)", e);
        }

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
        try {
            // JSON 문자열 → List<String> 변환
            List<String> role       = objectMapper.readValue(job.getJobRole(),       new TypeReference<List<String>>() {});
            List<String> need       = objectMapper.readValue(job.getJobNeed(),       new TypeReference<List<String>>() {});
            List<String> necessary  = objectMapper.readValue(job.getJobNecessary(),  new TypeReference<List<String>>() {});
            List<String> preference = objectMapper.readValue(job.getJobPreference(), new TypeReference<List<String>>() {});

            return JobResponseDTO.builder()
                    .id(job.getJobId())
                    .jobName(job.getJobName())
                    .jobCode(job.getJobCode())
                    .jobRole(role)
                    .jobNeed(need)
                    .jobNecessary(necessary)
                    .jobPreference(preference)
                    .teamId(job.getTeamId())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("JobResponseDTO 변환 실패: jobId=" + job.getJobId(), e);
        }
    }
}
