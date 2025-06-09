package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.employee.query.dao.DisciplinaryMapper;
import com.ddis.ddis_hr.employee.query.dto.MyDisciplinaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaryQueryServiceImpl implements DisciplinaryQueryService {

    private final DisciplinaryMapper disciplinaryMapper;
    private final S3Service s3Service;

    @Autowired
    public DisciplinaryQueryServiceImpl(DisciplinaryMapper disciplinaryMapper,
                                        S3Service s3Service) {
        this.disciplinaryMapper = disciplinaryMapper;
        this.s3Service = s3Service;
    }

    @Override
    public List<MyDisciplinaryDTO> findByEmployeeId(Long employeeId) {
        List<MyDisciplinaryDTO> list = disciplinaryMapper.findByEmployeeId(employeeId);

        // S3에서 다운로드용 presigned URL 생성
        for (MyDisciplinaryDTO dto : list) {
            String key = dto.getDisciplinaryFilePath();  // mapper에서 key(column)로 채워둔 값
            String url = s3Service.generateDownloadUrl(key, null);
            dto.setDisciplinaryFilePath(url);
        }
        return list;
    }
}


