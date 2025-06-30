package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.JobRequestDTO;
import com.ddis.ddis_hr.organization.command.application.dto.JobResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface JobService {
    JobResponseDTO createJob(JobRequestDTO request) throws JsonProcessingException;
    JobResponseDTO updateJob(Long jobId, JobRequestDTO request) throws JsonProcessingException;
    void deleteJob(Long jobId);
}
