package com.ddis.ddis_hr.employee.command.application.service;


import com.ddis.ddis_hr.employee.command.application.dto.DisciplinaryEnrollDTO;
import com.ddis.ddis_hr.employee.command.domain.aggregate.Disciplinary;
import com.ddis.ddis_hr.employee.command.domain.aggregate.DisciplinaryFile;
import com.ddis.ddis_hr.employee.command.domain.repository.DisciplinaryFileRepository;
import com.ddis.ddis_hr.employee.command.domain.repository.DisciplinaryRepository;
import com.ddis.ddis_hr.employee.query.dto.DisciplinaryFileDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class DisciplinaryServiceImpl implements DisciplinaryService {

    private final DisciplinaryRepository disciplinaryRepo;
    private final DisciplinaryFileRepository fileRepo;

    @Override
    public Long createDisciplinary(DisciplinaryEnrollDTO dto) {
        // 1) Disciplinary 엔티티 생성 (기본 생성자 + setter 사용)
        Disciplinary disc = new Disciplinary();
        disc.setEmployeeId(dto.getEmployeeId());
        disc.setDisciplinaryDescrip(dto.getDisciplinaryDescription());
        disc.setDisciplinaryDate(dto.getDisciplinaryDate());

        // 2) 파일 메타 추가
        if (dto.getFiles() != null) {
            for (DisciplinaryEnrollDTO.FileMeta fm : dto.getFiles()) {
                DisciplinaryFile file = new DisciplinaryFile();
                file.setFileName(fm.getFileName());
                file.setFileUrl(fm.getFileUrl());
                file.setFileSize(fm.getFileSize());
                file.setUploadedAt(LocalDateTime.now());
                disc.addFile(file);
            }
        }

        // 3) 저장 (cascade 로 파일도 함께 저장)
        disciplinaryRepo.save(disc);
        return disc.getDisciplinaryId();
    }

    @Override
    public void deleteDisciplinary(Long disciplinaryId) {
        Disciplinary disc = disciplinaryRepo.findById(disciplinaryId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 징계 id=" + disciplinaryId));
        disciplinaryRepo.delete(disc);
    }
}

