package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.FindDrafterMapper;
import com.ddis.ddis_hr.member.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrafterQueryServiceImpl implements DrafterQueryService {

    private final JwtUtil jwtUtil;
    private final FindDrafterMapper findDrafterMapper;

    @Override
    public FindDrafterQueryDTO getfindDrafterInfo(String token) {
        String empId = jwtUtil.getEmpIdFromToken(token);
        return findDrafterMapper.findDrafterInfo(empId);
    }

}
