package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dao.ContractMapper;
import com.ddis.ddis_hr.employee.query.dao.DisciplinaryMapper;
import com.ddis.ddis_hr.employee.query.dto.MyContractDTO;
import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractQueryServiceImpl implements ContractQueryService {

    private final ContractMapper contractMapper;
    private final S3Service s3Service;

    @Autowired
    public ContractQueryServiceImpl(ContractMapper contractMapper,
                                    S3Service s3Service) {
        this.contractMapper = contractMapper;
        this.s3Service = s3Service;
    }

    @Override
    public List<MyContractDTO> findByEmployeeId(Long employeeId) {
        // 일반 사원·HR 모두 자신의 목록 조회 가능
        return contractMapper.findByEmployeeId(employeeId);
    }

    @Override
    public List<MyContractDTO> findAllContracts() {
        // 인사팀 전용: 모든 사원 계약서 반환
        return contractMapper.findAllContracts();
    }

    @Override
    public MyContractDTO findOne(Long contractId, CustomUserDetails user) {
        // 1) 메타 조회
        MyContractDTO dto = contractMapper.findById(contractId);
        if (dto == null) {
            throw new EntityNotFoundException("계약서를 찾을 수 없습니다. id=" + contractId);
        }
        // 2) 권한 판정
        boolean isHr = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_HR"));
        // 3) 일반 사원은 본인 것만, HR은 전체 허용
        if (!isHr && !dto.getEmployeeId().equals(user.getEmployeeId())) {
            throw new SecurityException("권한이 없습니다. 이 계약서에 접근할 수 없습니다.");
        }
        return dto;
    }

    @Override
    public String generateDownloadUrl(Long contractId, CustomUserDetails user) {
        // findOne 내부에서 권한·소유권 검사
        MyContractDTO dto = findOne(contractId, user);
        // presigned URL 발급 (contentType 필요 시 지정)
        return s3Service.generateDownloadUrl(dto.getContractFileURL(), null);
    }
}
