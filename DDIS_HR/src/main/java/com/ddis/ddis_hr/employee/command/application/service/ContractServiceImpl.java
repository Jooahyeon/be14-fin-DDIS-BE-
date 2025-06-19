package com.ddis.ddis_hr.employee.command.application.service;

import com.ddis.ddis_hr.employee.command.application.dto.ContractEnrollDTO;
import com.ddis.ddis_hr.employee.command.domain.aggregate.Contract;
import com.ddis.ddis_hr.employee.command.domain.aggregate.ContractFile;
import com.ddis.ddis_hr.employee.command.domain.repository.ContractRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private final ContractRepository  contractRepository;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

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

        contractRepository.save(c);
        return c.getContractId();
    }

    @Override
    public void deleteContract(Long contractId) {
        Contract c = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계약 id=" + contractId));
        contractRepository.delete(c);
    }
}
