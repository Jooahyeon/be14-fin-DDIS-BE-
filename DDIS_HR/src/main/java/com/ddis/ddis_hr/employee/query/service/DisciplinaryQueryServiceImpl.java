package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dao.DisciplinaryMapper;
// import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import com.ddis.ddis_hr.employee.query.dto.MyDisciplinaryDTO;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.S3Config.service.S3Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaryQueryServiceImpl implements DisciplinaryQueryService {

    private final DisciplinaryMapper mapper;
    private final S3Service         s3Service;

    @Autowired
    public DisciplinaryQueryServiceImpl(DisciplinaryMapper mapper,
                                        S3Service s3Service) {
        this.mapper   = mapper;
        this.s3Service = s3Service;
    }

    @Override
    public List<MyDisciplinaryDTO> findByEmployeeId(Long employeeId) {
        return mapper.findByEmployeeId(employeeId);
    }

//    @Override
//    public List<DisciplinaryListDTO> findAll() {
//        return mapper.findAll();
//    }

    @Override
    public MyDisciplinaryDTO findOne(Integer disciplinaryId, CustomUserDetails user) {
        MyDisciplinaryDTO dto = mapper.findById(disciplinaryId);
        if (dto == null) {
            throw new EntityNotFoundException("해당 징계 기록을 찾을 수 없습니다. id=" + disciplinaryId);
        }
        boolean isHr = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_HR"));
        if (!isHr && !dto.getEmployeeId().equals(user.getEmployeeId())) {
            throw new SecurityException("권한이 없습니다. 이 징계 기록에 접근할 수 없습니다.");
        }
        return dto;
    }

    @Override
    public String generateDownloadUrl(Integer disciplinaryId, CustomUserDetails user) {
        MyDisciplinaryDTO dto = findOne(disciplinaryId, user);
        // presigned URL 생성 (dto.getDisciplinaryFileKey()가 S3 key)
        return s3Service.generateDownloadUrl(dto.getDisciplinaryFilePath(), null);
    }
}
