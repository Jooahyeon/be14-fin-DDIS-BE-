package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.HeadquartersDTO;
import com.ddis.ddis_hr.organization.command.application.dto.HeadquartersRequestDTO;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.HeadquartersEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.HeadquartersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HeadquartersServiceImpl implements HeadquartersService {

    private final HeadquartersRepository headquartersRepository;

    public HeadquartersServiceImpl(HeadquartersRepository headquartersRepository) {
        this.headquartersRepository = headquartersRepository;
    }

    @Override
    @Transactional
    public HeadquartersDTO createHeadquarters(HeadquartersRequestDTO requestDto) {
        // --- 1) 가장 마지막 headCode를 조회 ---
        HeadquartersEntity last = headquartersRepository.findTopByOrderByHeadCodeDesc();
        String newCode;
        if (last == null) {
            // 최초 생성인 경우 "H000" 기준으로 다음 "H001"로 시작
            newCode = "H001";
        } else {
            String lastCode = last.getHeadCode(); // 예: "H005"
            // 접두사 'H'를 떼어내고 숫자 부분만 파싱
            int num = Integer.parseInt(lastCode.substring(1)); // 5
            // +1 한 뒤 다시 zero-padding 3자리
            newCode = String.format("H%03d", num + 1); // "H006"
        }

        // --- 2) 엔티티에 데이터 설정 ---
        HeadquartersEntity entity = new HeadquartersEntity();
        entity.setHeadName(requestDto.getHeadName());
        entity.setHeadCode(newCode);

        // --- 3) 저장 후 DTO로 매핑하여 반환 ---
        HeadquartersEntity saved = headquartersRepository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public HeadquartersDTO updateHeadquarters(Long id, HeadquartersRequestDTO requestDto) {
        HeadquartersEntity existing = headquartersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Headquarters not found: " + id));

        existing.setHeadName(requestDto.getHeadName());
        // headCode는 수정하지 않고, 자동 생성된 코드를 그대로 유지합니다.

        HeadquartersEntity saved = headquartersRepository.save(existing);
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public void deleteHeadquarters(Long id) {
        HeadquartersEntity existing = headquartersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Headquarters not found: " + id));
        headquartersRepository.delete(existing);
    }

    private HeadquartersDTO mapToDto(HeadquartersEntity entity) {
        HeadquartersDTO dto = new HeadquartersDTO();
        dto.setHeadId(entity.getHeadId());
        dto.setHeadName(entity.getHeadName());
        dto.setHeadCode(entity.getHeadCode());
        // 부서 목록은 OrganizationTree 조회 시 채워 주므로 여기서는 null로 두거나 빈 리스트로 두셔도 됩니다.
        dto.setDepartment(null);
        return dto;
    }

}
