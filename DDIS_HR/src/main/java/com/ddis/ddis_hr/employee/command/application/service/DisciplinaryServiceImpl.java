package com.ddis.ddis_hr.employee.command.application.service;

import com.ddis.ddis_hr.employee.command.application.dto.DisciplinaryEnrollDTO;
import com.ddis.ddis_hr.employee.command.domain.aggregate.Disciplinary;
import com.ddis.ddis_hr.employee.command.domain.aggregate.DisciplinaryFile;
import com.ddis.ddis_hr.employee.command.domain.repository.DisciplinaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class DisciplinaryServiceImpl implements DisciplinaryService {

    private final DisciplinaryRepository disciplinaryRepository;

    @Autowired
    public DisciplinaryServiceImpl(DisciplinaryRepository disciplinaryRepository) {
        this.disciplinaryRepository = disciplinaryRepository;
    }

    @Override
    public Long createDisciplinary(DisciplinaryEnrollDTO dto) {
        Disciplinary disc = new Disciplinary();
        disc.setEmployeeId(dto.getEmployeeId());
        disc.setDisciplinaryDescrip(dto.getDisciplinaryDescription());
        disc.setDisciplinaryDate(dto.getDisciplinaryDate());

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

        disciplinaryRepository.save(disc);
        return disc.getDisciplinaryId();
    }

    @Override
    public void deleteDisciplinary(Long disciplinaryId) {
        Disciplinary disc = disciplinaryRepository.findById(disciplinaryId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 징계 id=" + disciplinaryId));
        disciplinaryRepository.delete(disc);
    }
}

