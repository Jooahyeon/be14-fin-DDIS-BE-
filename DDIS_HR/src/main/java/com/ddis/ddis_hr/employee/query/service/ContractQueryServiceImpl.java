package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dto.MyContractDTO;
import com.ddis.ddis_hr.employee.query.dao.ContractMapper;
import com.ddis.ddis_hr.S3Config.service.S3Service;
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
        List<MyContractDTO> list = contractMapper.findByEmployeeId(employeeId);

        // S3에서 다운로드용 presigned URL 생성
        for (MyContractDTO dto : list) {
            String key = dto.getContractFileURL();  // mapper에서 key(column)로 채워둔 값
            String url = s3Service.generateDownloadUrl(key, null);
            dto.setContractFileURL(url);
        }
        return list;
    }
}
