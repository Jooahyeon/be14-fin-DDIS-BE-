package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.JobRequestDTO;
import com.ddis.ddis_hr.organization.command.application.dto.JobResponseDTO;

public interface JobService {
    JobResponseDTO createJob(JobRequestDTO request);
    JobResponseDTO updateJob(Long jobId, JobRequestDTO request);
    void deleteJob(Long jobId);
}
