package com.ddis.ddis_hr.organization.command.application.controller;

import com.ddis.ddis_hr.organization.command.application.dto.JobRequestDTO;
import com.ddis.ddis_hr.organization.command.application.dto.JobResponseDTO;
import com.ddis.ddis_hr.organization.command.application.service.JobService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/org")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PreAuthorize("hasAnyRole('HR')")
    @PostMapping("/create/job")
    public ResponseEntity<JobResponseDTO> createJob(
            @RequestBody JobRequestDTO request) throws JsonProcessingException {
        JobResponseDTO created = jobService.createJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasAnyRole('HR')")
    @PutMapping("/update/job/{id}")
    public ResponseEntity<JobResponseDTO> updateJob(
            @PathVariable("id") Long jobId,
            @RequestBody JobRequestDTO request) throws JsonProcessingException {
        JobResponseDTO updated = jobService.updateJob(jobId, request);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('HR')")
    @DeleteMapping("/delete/job/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }
}
