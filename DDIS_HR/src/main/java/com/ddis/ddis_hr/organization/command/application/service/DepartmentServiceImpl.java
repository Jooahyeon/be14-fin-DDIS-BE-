package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.DepartmentDTO;
import com.ddis.ddis_hr.organization.command.application.dto.DepartmentRequestDTO;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.DepartmentEntity;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.HeadquartersEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.DepartmentRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.HeadquartersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final HeadquartersRepository headquartersRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 HeadquartersRepository headquartersRepository) {
        this.departmentRepository = departmentRepository;
        this.headquartersRepository = headquartersRepository;
    }

    /** 부서 생성(Create) **/
    @Override
    @Transactional
    public DepartmentDTO createDepartment(DepartmentRequestDTO requestDto) {
        // 1) headName으로 본부 조회
        HeadquartersEntity head = headquartersRepository
                .findByHeadId(requestDto.getHeadId())
                .orElseThrow(() ->
                        new RuntimeException("해당 이름의 본부를 찾을 수 없습니다: " + requestDto.getHeadId()));

        // 2) departmentCode 자동 생성
        DepartmentEntity last = departmentRepository.findTopByOrderByDepartmentCodeDesc();
        String newCode;
        if (last == null) {
            newCode = "D001";
        } else {
            String lastCode = last.getDepartmentCode(); // 예: "D005"
            int num = Integer.parseInt(lastCode.substring(1)); // 5
            newCode = String.format("D%03d", num + 1);         // "D006"
        }

        // 3) 엔티티 생성 및 필드 설정
        DepartmentEntity entity = new DepartmentEntity();
        entity.setDepartmentName(requestDto.getDepartmentName());
        entity.setDepartmentCode(newCode);
        entity.setHeadquarters(head);

        // 4) 저장 후 DTO 반환
        DepartmentEntity saved = departmentRepository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentRequestDTO requestDto) {
        DepartmentEntity existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다: " + id));

        // 1) headName으로 본부 조회
        HeadquartersEntity head = headquartersRepository
                .findByHeadId(requestDto.getHeadId())
                .orElseThrow(() ->
                        new RuntimeException("해당 이름의 본부를 찾을 수 없습니다: " + requestDto.getHeadId()));

        // 2) 본부 연결 변경
        existing.setHeadquarters(head);

        // 3) 부서명만 수정 (코드는 서비스가 자동 생성했던 값을 그대로 유지)
        existing.setDepartmentName(requestDto.getDepartmentName());

        DepartmentEntity saved = departmentRepository.save(existing);
        return mapToDto(saved);
    }


    /** 부서 삭제(Delete) **/
    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        DepartmentEntity existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다: " + id));
        departmentRepository.delete(existing);
    }

    /** Entity → DTO 매핑 **/
    private DepartmentDTO mapToDto(DepartmentEntity entity) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setDepartmentCode(entity.getDepartmentCode());
        dto.setTeam(null); // 팀 정보는 트리 조회 시 OrganizationService에서 채워줍니다.
        return dto;
    }
}
