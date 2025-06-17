package com.ddis.ddis_hr.employee.command.application.service;



import com.ddis.ddis_hr.employee.command.application.dto.ContractEnrollDTO;
import com.ddis.ddis_hr.employee.command.domain.aggregate.Contract;
import com.ddis.ddis_hr.employee.command.domain.aggregate.ContractFile;
import com.ddis.ddis_hr.employee.command.domain.repository.ContractRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractServiceImpl implements ContractService {

    private final ContractRepository repo;

    @Override
    public Long createContract(ContractEnrollDTO dto) {
        Contract c = new Contract();
        c.setContractDescrip(dto.getContractDescrip());
        c.setRequestDate(dto.getRequestDate());
        c.setContractDate(dto.getContractDate());
        c.setEndDate(dto.getEndDate());
        c.setEmployeeId(dto.getEmployeeId());

        if (dto.getFiles() != null) {
            for (ContractEnrollDTO.FileMeta fm : dto.getFiles()) {
                ContractFile f = new ContractFile();
                f.setFileName(fm.getFileName());
                f.setFileUrl(fm.getFileUrl());
                f.setFileSize(fm.getFileSize());
                f.setUploadedAt(LocalDateTime.now());
                c.addFile(f);
            }
        }

        repo.save(c);
        return c.getContractId();
    }

    @Override
    public void deleteContract(Long contractId) {
        Contract c = repo.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계약 id=" + contractId));
        repo.delete(c);
    }
}
